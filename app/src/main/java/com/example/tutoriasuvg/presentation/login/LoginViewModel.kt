package com.example.tutoriasuvg.presentation.login

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.local.TutoriasDatabase
import com.example.tutoriasuvg.data.repository.UserRepositoryImpl
import kotlinx.coroutines.launch
import com.example.tutoriasuvg.data.local.SessionManager

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = TutoriasDatabase.getDatabase(application).userDao()
    private val userRepository = UserRepositoryImpl(userDao)
    private val sessionManager = SessionManager(application)

    var email = mutableStateOf("")
        private set

    var showError = mutableStateOf(false)
        private set

    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
    }

    fun onLoginClicked(userType: String) {
        viewModelScope.launch {
            val user = userRepository.loginUserByEmail(email.value)
            if (user != null && (userType == "user" && !user.isTutor || userType == "tutor" && user.isTutor || userType == "admin")) {
                sessionManager.saveLoginSession(email.value, userType)
                showError.value = false
            } else {
                showError.value = true
            }
        }
    }
}

