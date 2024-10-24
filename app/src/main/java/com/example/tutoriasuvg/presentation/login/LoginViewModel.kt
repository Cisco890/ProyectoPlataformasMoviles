package com.example.tutoriasuvg.presentation.login

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.local.TutoriasDatabase
import com.example.tutoriasuvg.data.repository.UserRepositoryImpl
import com.example.tutoriasuvg.domain.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = TutoriasDatabase.getDatabase(application).userDao()
    private val userRepository = UserRepositoryImpl(userDao)

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
        viewModelScope.launch {
            val user = userRepository.loginUser(email.value, password.value)
            showError.value = user == null
        }
    }
}