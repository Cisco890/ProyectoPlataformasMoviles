package com.example.tutoriasuvg.presentation.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var name = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var confirmPassword = mutableStateOf("")
        private set

    var isTutor = mutableStateOf(false)
        private set

    fun onNameChanged(newName: String) {
        name.value = newName
    }

    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChanged(newPasswordCanged: String) {
        password.value = newPasswordCanged
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun onTutorChecked(isChecked: Boolean) {
        isTutor.value = isChecked
    }
}