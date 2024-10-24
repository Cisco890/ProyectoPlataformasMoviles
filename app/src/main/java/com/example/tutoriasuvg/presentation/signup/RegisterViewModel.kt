package com.example.tutoriasuvg.presentation.signup

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.local.TutoriasDatabase
import com.example.tutoriasuvg.data.local.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = TutoriasDatabase.getDatabase(application).userDao()

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

    fun onRegisterClicked() {
        viewModelScope.launch {
            if (_password.value == _confirmPassword.value) {
                val user = User(
                    name = _name.value,
                    email = _email.value,
                    password = _password.value,
                    isTutor = _isTutor.value
                )
                userDao.insertUser(user)
            }
        }
    }
}