package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class Estudiante(
    val nombre: String,
    val carnet: String,
    val anio: String
)

class PerfilEstudianteViewModel : ViewModel() {

    // StateFlow to hold the student profile data
    private val _estudiante = MutableStateFlow(
        Estudiante(
            nombre = "Nombre del Estudiante",
            carnet = "Carnet del Estudiante",
            anio = "Año que Cursa el Estudiante"
        )
    )
    val estudiante: StateFlow<Estudiante> = _estudiante

    // StateFlow to track if the student wants to become a tutor
    private val _isTutor = MutableStateFlow(false)
    val isTutor: StateFlow<Boolean> = _isTutor

    // Function to handle "Volverme Tutor" action
    fun volvermeTutor() {
        _isTutor.update { true }
    }
}
