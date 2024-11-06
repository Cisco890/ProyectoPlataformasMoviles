package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DetallesTutoriaEstudiantesViewModel : ViewModel() {

    private val _tutoriasEsList = MutableStateFlow<List<TutoriasEs>>(emptyList())
    val tutoriasEsList: StateFlow<List<TutoriasEs>> = _tutoriasEsList

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted

    // Método para completar una tutoría específica
    fun completarTutoriaEs(tutoriaEs: TutoriasEs) {
        _tutoriasEsList.update { tutorias ->
            tutorias.map { if (it == tutoriaEs) it.copy(link = "Completado") else it }
        }
        _isCompleted.update { true }
    }

    // Método para agregar tutorías a la lista
    fun agregarTutoriaEs(tutoriaEs: TutoriasEs) {
        _tutoriasEsList.update { tutorias -> tutorias + tutoriaEs }
    }
}
