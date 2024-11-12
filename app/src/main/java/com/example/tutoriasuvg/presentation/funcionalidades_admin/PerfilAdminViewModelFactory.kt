package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository

class PerfilAdminViewModelFactory(
    private val loginRepository: FirebaseLoginRepository,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilAdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerfilAdminViewModel(loginRepository, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
