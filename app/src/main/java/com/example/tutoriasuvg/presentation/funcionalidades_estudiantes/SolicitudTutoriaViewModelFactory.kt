package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.SolicitudRepository

class SolicitudTutoriaViewModelFactory(
    private val solicitudRepository: SolicitudRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SolicitudTutoriaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SolicitudTutoriaViewModel(solicitudRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
