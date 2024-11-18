package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.SolicitudRepository

class SolicitudTutoriaViewModelFactory(
    private val solicitudRepository: SolicitudRepository,
    private val studentId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SolicitudTutoriaViewModel::class.java)) {
            return SolicitudTutoriaViewModel(
                solicitudRepository = solicitudRepository,
                studentId = studentId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

