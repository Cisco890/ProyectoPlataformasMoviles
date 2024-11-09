package com.example.tutoriasuvg.presentation.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    application: Application,
    private val registerRepository: FirebaseRegisterRepository
) : AndroidViewModel(application) {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _carnet = MutableStateFlow("")
    val carnet: StateFlow<String> = _carnet

    private val _year = MutableStateFlow("")
    val year: StateFlow<String> = _year

    private val _isTutor = MutableStateFlow(false)
    val isTutor: StateFlow<Boolean> = _isTutor

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered

    // Functions to update each field
    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun onCarnetChanged(newCarnet: String) {
        _carnet.value = newCarnet
    }

    fun onYearChanged(newYear: String) {
        _year.value = newYear
    }

    fun onTutorChecked(isChecked: Boolean) {
        _isTutor.value = isChecked
    }

    fun onRegisterClicked() {
        _errorMessage.value = ""

        if (_password.value != _confirmPassword.value) {
            _errorMessage.value = "Las contraseñas no coinciden"
            return
        }

        if (_password.value.length < 6) {
            _errorMessage.value = "La contraseña debe tener al menos 6 caracteres"
            return
        }

        if (_name.value.isEmpty() || _email.value.isEmpty() || _password.value.isEmpty() || _carnet.value.isEmpty() || _year.value.isEmpty()) {
            _errorMessage.value = "Todos los campos son obligatorios"
            return
        }

        viewModelScope.launch {
            val result = registerRepository.registerUser(
                email = _email.value,
                password = _password.value,
                name = _name.value,
                userType = if (_isTutor.value) "tutor" else "student",
                carnet = _carnet.value,
                year = _year.value
            )
            result.fold(
                onSuccess = {
                    _isRegistered.value = true
                    _errorMessage.value = "Registro exitoso"
                },
                onFailure = { exception ->
                    _errorMessage.value = "Error al registrar el usuario: ${exception.message}"
                }
            )
        }
    }
}
