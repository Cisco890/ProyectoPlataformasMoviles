package com.example.tutoriasuvg.data.repository

import com.example.tutoriasuvg.data.model.Solicitud
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SolicitudRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    init {
        observeSolicitudesEnTiempoReal()
    }

    private fun observeSolicitudesEnTiempoReal() {
        firestore.collection("solicitudes")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }

                snapshot?.let {
                    val solicitudList = it.documents.mapNotNull { doc ->
                        doc.toObject(Solicitud::class.java)?.copy(id = doc.id)
                    }
                    coroutineScope.launch {
                        _solicitudes.emit(solicitudList)
                    }
                }
            }
    }

    fun obtenerTodasLasSolicitudes(): Flow<List<Solicitud>> = _solicitudes

    fun obtenerSolicitudesParaEstudiante(studentId: String): Flow<List<Solicitud>> {
        return _solicitudes.map { solicitudes ->
            solicitudes.filter { it.studentId == studentId && !it.completed }
        }
    }

    fun obtenerSolicitudesParaTutor(tutorId: String): Flow<List<Solicitud>> {
        return _solicitudes.map { solicitudes ->
            solicitudes.filter { it.tutorId == tutorId }
        }
    }

    suspend fun asignarTutor(
        solicitud: Solicitud,
        tutorId: String,
        date: String? = null,
        location: String? = null,
        time: String? = null
    ) {
        try {
            val updateData = mutableMapOf<String, Any>(
                "tutorId" to tutorId
            ).apply {
                date?.let { this["date"] = it }
                location?.let { this["location"] = it }
                time?.let { this["time"] = it }
            }

            firestore.collection("solicitudes").document(solicitud.id).update(updateData).await()

            _solicitudes.update { current ->
                current.map { existing ->
                    if (existing.id == solicitud.id) {
                        existing.copy(
                            tutorId = tutorId,
                            date = date,
                            location = location,
                            time = time
                        )
                    } else existing
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun enviarSolicitudTutor(
        courseName: String,
        days: List<String>,
        shift: String,
        studentId: String
    ) {
        try {
            val existing = firestore.collection("solicitudes")
                .whereEqualTo("courseName", courseName)
                .whereEqualTo("shift", shift)
                .whereEqualTo("studentId", studentId)
                .get()
                .await()

            if (existing.isEmpty) {
                val nuevaSolicitud = Solicitud(
                    courseName = courseName,
                    days = days,
                    shift = shift,
                    studentId = studentId,
                    tutorId = null,
                    completed = false
                )
                firestore.collection("solicitudes").add(nuevaSolicitud).await()
            } else {
                println("Ya existe una solicitud para este curso y estudiante.")
            }
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
                transaction.update(tutorDocRef, "completedHours", currentHours + 1.5)
            }.await()

            _solicitudes.update { current ->
                current.map { solicitud ->
                    if (solicitud.id == tutoriaId) solicitud.copy(completed = true) else solicitud
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun eliminarSolicitud(tutoriaId: String) {
        try {
            firestore.collection("solicitudes").document(tutoriaId).delete().await()

            _solicitudes.update { current ->
                current.filter { it.id != tutoriaId }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
