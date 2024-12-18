package com.example.tutoriasuvg.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application,
    private val loginRepository: FirebaseLoginRepository,
    private val sessionManager: SessionManager
) : AndroidViewModel(application) {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClicked(onLoginSuccess: (String) -> Unit) {
        if (_email.value.isEmpty() || _password.value.isEmpty()) {
            _errorMessage.value = "Ingrese su correo y contraseña."
            return
        }

        _isLoading.value = true
        _errorMessage.value = ""

        viewModelScope.launch {
            val loginResult = loginRepository.login(_email.value, _password.value)
            _isLoading.value = false
            loginResult.fold(
                onSuccess = { userId ->
                    saveSession(userId, onLoginSuccess)
                },
                onFailure = { exception ->
                    _errorMessage.value = "Error al iniciar sesión: ${exception.message}"
                }
            )
        }
    }

    private fun saveSession(userId: String, onLoginSuccess: (String) -> Unit) {
        viewModelScope.launch {
            val userTypeResult = loginRepository.getUserType(userId)
            userTypeResult.fold(
                onSuccess = { userType ->
                    Log.d("LoginViewModel", "Saving session with userType=$userType, email=${_email.value}, identifier=$userId, isUsingCarnet=true")
                    sessionManager.saveUserSession(userType, _email.value, identifier = userId, isUsingCarnet = true)
                    onLoginSuccess(userType)
                },
                onFailure = { exception ->
                    _errorMessage.value = "Error al obtener el tipo de usuario: ${exception.message}"
                }
            )
        }
    }

}
