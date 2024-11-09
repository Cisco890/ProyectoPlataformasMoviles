package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

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

    fun volvermeTutor() {
        _isTutor.update { true }
    }

    fun logout() {
        loginRepository.logout()
    }
}
