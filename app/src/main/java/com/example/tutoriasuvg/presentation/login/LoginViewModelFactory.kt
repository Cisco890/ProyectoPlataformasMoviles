package com.example.tutoriasuvg.presentation.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository

class LoginViewModelFactory(
    private val application: Application,
    private val loginRepository: FirebaseLoginRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(application, loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
