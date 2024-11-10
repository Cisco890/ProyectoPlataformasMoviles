package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProgresoHorasBecaViewModel(
    private val args: ProgresoHorasBecaArgs,
    private val sessionManager: SessionManager,
    private val loginRepository: FirebaseLoginRepository
) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow(ProgresoHorasBecaUIState())
    val uiState: StateFlow<ProgresoHorasBecaUIState> = _uiState

    init {
        loadTutorProfileData()
    }

    private fun loadTutorProfileData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val querySnapshot = if (args.isUsingCarnet) {
                    db.collection("users").whereEqualTo("carnet", args.identifier).get().await()
                } else {
                    db.collection("users").whereEqualTo("email", args.identifier).get().await()
                }

                if (querySnapshot.documents.isNotEmpty()) {
                    val document = querySnapshot.documents[0]
                    val nombre = document.getString("name") ?: "Nombre desconocido"
                    val carnet = document.getString("carnet") ?: "Sin carnet"
                    val anio = document.getString("year") ?: "Año no definido"
                    val completadas = document.getLong("completedHours")?.toInt() ?: 0
                    val total = document.getLong("hours")?.toInt() ?: 0
                    val progreso = if (total > 0) (completadas.toFloat() / total).coerceIn(0f, 1f) else 0f

                    _uiState.value = ProgresoHorasBecaUIState(
                        isLoading = false,
                        nombreEstudiante = nombre,
                        carnetEstudiante = carnet,
                        anioEstudio = anio,
                        horasCompletadas = completadas,
                        totalHoras = total,
                        porcentajeProgreso = progreso
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Usuario no encontrado"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar datos"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                loginRepository.logout()
                sessionManager.clearSession()
                _uiState.value = _uiState.value.copy(isLoggingOut = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoggingOut = false,
                    errorMessage = "Error al cerrar sesión: ${e.message}"
                )
            }
        }
    }

}
