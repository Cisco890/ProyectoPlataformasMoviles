package com.example.tutoriasuvg.data.repository

import com.example.tutoriasuvg.data.model.Solicitud
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SolicitudRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    init {
        observeSolicitudesInRealTime()
    }

    private fun observeSolicitudesInRealTime() {
        firestore.collection("solicitudes").addSnapshotListener { snapshot, error ->
            if (error != null) {
                error.printStackTrace()
                return@addSnapshotListener
            }
            snapshot?.let {
                val solicitudList = it.documents.mapNotNull { doc -> doc.toObject(Solicitud::class.java) }
                coroutineScope.launch {
                    _solicitudes.emit(solicitudList)
                }
            }
        }
    }

    fun getAllSolicitudes(): StateFlow<List<Solicitud>> {
        return solicitudes
    }

    fun getAsignacionesParaEstudiante(): StateFlow<List<Solicitud>> {
        return _solicitudes
            .map { solicitudes ->
                solicitudes.filter { it.tutorId != null && !it.completed }
            }
            .stateIn(
                scope = coroutineScope,
                started = kotlinx.coroutines.flow.SharingStarted.Lazily,
                initialValue = emptyList()
            )
    }

    fun getAsignacionesParaTutor(tutorId: String): StateFlow<List<Solicitud>> {
        return _solicitudes
            .map { solicitudes ->
                solicitudes.filter { it.tutorId == tutorId && !it.completed }
            }
            .stateIn(
                scope = coroutineScope,
                started = kotlinx.coroutines.flow.SharingStarted.Lazily,
                initialValue = emptyList()
            )
    }

    suspend fun asignarTutor(solicitud: Solicitud, tutorId: String, date: String? = null, location: String? = null, time: String? = null) {
        try {
            val updateData = mutableMapOf<String, Any>(
                "tutorId" to tutorId
            )
            date?.let { updateData["date"] = it }
            location?.let { updateData["location"] = it }
            time?.let { updateData["time"] = it }

            firestore.collection("solicitudes").document(solicitud.id).update(updateData).await()

            _solicitudes.value = _solicitudes.value.map { s ->
                if (s.id == solicitud.id) s.copy(tutorId = tutorId, date = date, location = location, time = time) else s
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun sendTutoriaRequest(courseName: String, days: List<String>, shift: String) {
        val solicitud = Solicitud(
            courseName = courseName,
            days = days,
            shift = shift
        )
        try {
            val newDocRef = firestore.collection("solicitudes").add(solicitud).await()
            val updatedSolicitud = solicitud.copy(id = newDocRef.id)
            _solicitudes.value = _solicitudes.value + updatedSolicitud
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun completarTutoria(tutoriaId: String, tutorId: String) {
        try {
            firestore.collection("solicitudes").document(tutoriaId).update("completed", true).await()

            val tutorDocRef = firestore.collection("users").document(tutorId)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(tutorDocRef)
                val currentHours = snapshot.getDouble("completedHours") ?: 0.0
                val updatedHours = currentHours + 1.30
                transaction.update(tutorDocRef, "completedHours", updatedHours)
            }.await()

            _solicitudes.value = _solicitudes.value.map { solicitud ->
                if (solicitud.id == tutoriaId) solicitud.copy(completed = true) else solicitud
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun eliminarTutoriaEstudiante(tutoriaId: String) {
        try {
            firestore.collection("solicitudes").document(tutoriaId).delete().await()

            _solicitudes.value = _solicitudes.value.filter { it.id != tutoriaId }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
