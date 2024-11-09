package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Estudiante(
    val nombre: String,
    val carnet: String,
    val anio: String
)

class PerfilEstudianteViewModel(
    private val loginRepository: FirebaseLoginRepository
) : ViewModel() {

    private val _estudiante = MutableStateFlow(
        Estudiante(
            nombre = "Nombre del Estudiante",
            carnet = "Carnet del Estudiante",
            anio = "Año que Cursa el Estudiante"
        )
    )
    val estudiante: StateFlow<Estudiante> = _estudiante

    private val _isTutor = MutableStateFlow(false)
    val isTutor: StateFlow<Boolean> = _isTutor

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchEstudianteInfo()
    }

    private fun fetchEstudianteInfo() {
        viewModelScope.launch {
            val userId = loginRepository.getCurrentUserId()
            if (userId != null) {
                val result = loginRepository.getUserType(userId)
                result.fold(
                    onSuccess = { userType ->
                        _isTutor.value = userType == "tutor"
                        loadUserData(userId)
                    },
                    onFailure = { exception ->
                        _errorMessage.value = "Error al obtener el tipo de usuario: ${exception.message}"
                    }
                )
            } else {
                _errorMessage.value = "Usuario no encontrado."
            }
        }
    }

    private fun loadUserData(userId: String) {
        viewModelScope.launch {
            val result = loginRepository.getUserData(userId)
            result.fold(
                onSuccess = { data ->
                    val nombre = data["name"] as? String ?: "Desconocido"
                    val carnet = data["carnet"] as? String ?: "Desconocido"
                    val anio = data["anio"] as? String ?: "Desconocido"
                    _estudiante.update { Estudiante(nombre, carnet, anio) }
                },
                onFailure = { exception ->
                    _errorMessage.value = "Error al cargar los datos del estudiante: ${exception.message}"
                }
            )
        }
    }

    fun volvermeTutor() {
        viewModelScope.launch {
            _isTutor.update { true }
            val userId = loginRepository.getCurrentUserId()
            if (userId != null) {
                val result = loginRepository.updateUserType(userId, "tutor")
                result.onFailure { exception ->
                    _errorMessage.value = "Error al actualizar a tutor: ${exception.message}"
                }
            }
        }
    }

    fun logout() {
        loginRepository.logout()
    }
}
