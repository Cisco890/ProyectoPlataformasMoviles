package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.TutoriaRepository

class DetallesTutoriaEstudiantesViewModelFactory(
    private val tutoriaRepository: TutoriaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetallesTutoriaEstudiantesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetallesTutoriaEstudiantesViewModel(tutoriaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
