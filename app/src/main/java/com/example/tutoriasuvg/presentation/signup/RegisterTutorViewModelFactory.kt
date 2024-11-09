package com.example.tutoriasuvg.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository

class RegisterTutorViewModelFactory(
    private val registerRepository: FirebaseRegisterRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterTutorViewModel::class.java)) {
            return RegisterTutorViewModel(registerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

