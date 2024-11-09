package com.example.tutoriasuvg.presentation.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository

class RegisterViewModelFactory(
    private val application: Application,
    private val registerRepository: FirebaseRegisterRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(application, registerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
