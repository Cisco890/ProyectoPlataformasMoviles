package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.Serializable

// Data class to hold profile information
data class PerfilTutorData(
    val name: String,
    val carnet: String,
    val year: String,
    val hours: Int,
    val totalHours: Int,
    val progress: Float
) : Serializable

private const val TAG = "PerfilTutorViewModel"

class PerfilTutorViewModel(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val crashlytics: FirebaseCrashlytics = FirebaseCrashlytics.getInstance(),
    private val loginRepository: FirebaseLoginRepository = FirebaseLoginRepository(),
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _profileData = MutableStateFlow<PerfilTutorData?>(null)
    val profileData: StateFlow<PerfilTutorData?> = _profileData

    fun loadProfileData() {
        viewModelScope.launch {
            val userId = sessionManager.getUserIdentifierSync()
            if (userId == null) {
                logError("Usuario no logueado", "User is not logged in.")
            } else {
                try {
                    val document = firestore.collection("users").document(userId).get().await()
                    if (document.exists()) {
                        val name = document.getString("name") ?: "Nombre desconocido"
                        val carnet = document.getString("carnet") ?: "Carnet desconocido"
                        val year = document.getString("year") ?: "Año desconocido"
                        val hours = (document.getLong("totalHours") ?: 0).toInt()
                        val totalHours = (document.getLong("hours") ?: 10).toInt()
                        val progress = calculateProgress(hours, totalHours)

                        _profileData.value = PerfilTutorData(name, carnet, year, hours, totalHours, progress)
                    } else {
                        logError("Documento de usuario no encontrado.", "User document not found for ID $userId.")
                    }
                } catch (e: Exception) {
                    logError("Error al cargar datos del perfil: ${e.message}", "Failed to load profile data for user ID $userId", e)
                }
            }
        }
    }


    private fun calculateProgress(hours: Int, totalHours: Int): Float {
        return if (totalHours > 0) hours / totalHours.toFloat() else 0f
    }

    private fun logError(logMessage: String, crashlyticsMessage: String, exception: Exception? = null) {
        Log.e(TAG, logMessage)
        crashlytics.log(crashlyticsMessage)
        exception?.let { crashlytics.recordException(it) }
    }

    suspend fun logout() {
        loginRepository.logout()
        sessionManager.clearSession()
    }
}
