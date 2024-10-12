package com.example.tutoriasuvg.presentation.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _isTutor = MutableStateFlow(false)
    val isTutor: MutableStateFlow<Boolean> = _isTutor

    fun onNameChanged(newName: String){
        _name.value = newName
    }

    fun onEmailChanged(newEmail: String){
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String){
        _password.value = newPassword
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String){
        _confirmPassword.value = newConfirmPassword
    }

    fun onTutorChecked(isChecked: Boolean){
        _isTutor.value = isChecked
    }
}