package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DetallesTutoriaViewModel : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted

    fun loadSolicitudState(solicitudId: String) {
        viewModelScope.launch {
            try {
                val completed = firestore.collection("solicitudes")
                    .document(solicitudId)
                    .get()
                    .await()
                    .getBoolean("completed") ?: false

                _isCompleted.value = completed
            } catch (e: Exception) {
                logError("Error al cargar el estado de la solicitud: ${e.message}", e)
            }
        }
    }

    fun completarTutoria(userId: String, solicitudId: String) {
        viewModelScope.launch {
            try {
                val currentHours = firestore.collection("users").document(userId)
                    .get().await()
                    .getDouble("completedHours")?.toFloat() ?: 0.0f

                val newHours = currentHours + 1.3f

                firestore.collection("users").document(userId)
                    .update("completedHours", newHours)
                    .await()

                firestore.collection("solicitudes").document(solicitudId)
                    .update("completed", true)
                    .await()

                _isCompleted.value = true
            } catch (e: Exception) {
                logError("Error al completar tutoría: ${e.message}", e)
            }
        }
    }

    private fun logError(message: String, exception: Exception) {
        println(message)
        exception.printStackTrace()
    }
}
