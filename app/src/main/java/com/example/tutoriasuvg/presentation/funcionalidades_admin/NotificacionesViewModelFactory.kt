package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import com.example.tutoriasuvg.data.repository.TutorRepository

class NotificacionesViewModelFactory(
    private val solicitudRepository: SolicitudRepository,
    private val tutorRepository: TutorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificacionesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificacionesViewModel(
                solicitudRepository = solicitudRepository,
                tutorRepository = tutorRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
