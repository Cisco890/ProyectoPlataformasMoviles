package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.SolicitudRepository

class DetallesTutoriaEstudiantesViewModelFactory(
    private val solicitudRepository: SolicitudRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetallesTutoriaEstudiantesViewModel::class.java)) {
            return DetallesTutoriaEstudiantesViewModel(solicitudRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

