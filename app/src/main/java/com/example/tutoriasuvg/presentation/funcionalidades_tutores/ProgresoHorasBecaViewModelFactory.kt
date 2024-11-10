package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository

class ProgresoHorasBecaViewModelFactory(
    private val args: ProgresoHorasBecaArgs,
    private val sessionManager: SessionManager,
    private val loginRepository: FirebaseLoginRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgresoHorasBecaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgresoHorasBecaViewModel(args, sessionManager, loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
