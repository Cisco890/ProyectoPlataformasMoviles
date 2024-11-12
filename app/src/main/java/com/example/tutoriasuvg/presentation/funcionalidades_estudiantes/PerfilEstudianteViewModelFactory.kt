package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository

class PerfilEstudianteViewModelFactory(
    private val loginRepository: FirebaseLoginRepository,
    private val registerRepository: FirebaseRegisterRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilEstudianteViewModel::class.java)) {
            return PerfilEstudianteViewModel(loginRepository, registerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


