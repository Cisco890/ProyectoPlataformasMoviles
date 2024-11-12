package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
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
    private val loginRepository: FirebaseLoginRepository,
    private val registerRepository: FirebaseRegisterRepository
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

    fun volvermeTutor(
        hours: Int = 0,
        courses: List<String> = emptyList(),
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val userId = loginRepository.getCurrentUserId()
            if (userId != null) {
                val updateTypeResult = loginRepository.updateUserType(userId, "tutor")
                if (updateTypeResult.isSuccess) {
                    val registerResult = registerRepository.registerTutorData(userId, hours, courses)
                    registerResult.fold(
                        onSuccess = {
                            _isTutor.value = true
                            onSuccess()
                        },
                        onFailure = { exception ->
                            _errorMessage.value = "Error al registrar datos de tutor: ${exception.message}"
                            onFailure(_errorMessage.value ?: "Error desconocido")
                        }
                    )
                } else {
                    val exception = updateTypeResult.exceptionOrNull()
                    _errorMessage.value = "Error al actualizar el tipo de usuario: ${exception?.message}"
                    onFailure(_errorMessage.value ?: "Error desconocido")
                }
            } else {
                val message = "Usuario no encontrado."
                _errorMessage.value = message
                onFailure(message)
            }
        }
    }

    fun logout() {
        loginRepository.logout()
    }
}
