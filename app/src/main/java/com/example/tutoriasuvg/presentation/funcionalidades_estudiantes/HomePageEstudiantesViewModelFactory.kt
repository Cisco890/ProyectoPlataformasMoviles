package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.SolicitudRepository

class HomePageEstudiantesViewModelFactory(
    private val solicitudRepository: SolicitudRepository,
    private val studentId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageEstudiantesViewModel::class.java)) {
            return HomePageEstudiantesViewModel(solicitudRepository, studentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

