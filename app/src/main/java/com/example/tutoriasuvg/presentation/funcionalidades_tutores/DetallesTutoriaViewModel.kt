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

    fun completarTutoria(userId: String) {
        viewModelScope.launch {
            try {
                val currentHours = firestore.collection("users").document(userId)
                    .get().await()
                    .getDouble("completedHours")?.toFloat() ?: 0.0f

                val newHours = currentHours + 1.3f

                firestore.collection("users").document(userId)
                    .update("completedHours", newHours)
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

