package com.example.tutoriasuvg.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(){

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var showError = mutableStateOf(false)
        private set

    fun onEmailChanged(newEmail: String){
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String){
        password.value = newPassword
    }

    fun onLoginClicked(){
        showError.value = email.value.isEmpty() || password.value.isEmpty()
    }
}