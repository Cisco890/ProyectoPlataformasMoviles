package com.example.tutoriasuvg.presentation.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Campos comunes para todos los usuarios
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _isTutor = MutableStateFlow(false)
    val isTutor: StateFlow<Boolean> = _isTutor

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered

    // Métodos de actualización de estado
    fun onNameChanged(newName: String) { _name.value = newName }
    fun onEmailChanged(newEmail: String) { _email.value = newEmail }
    fun onPasswordChanged(newPassword: String) { _password.value = newPassword }
    fun onConfirmPasswordChanged(newConfirmPassword: String) { _confirmPassword.value = newConfirmPassword }
    fun onTutorChecked(isChecked: Boolean) { _isTutor.value = isChecked }

    // Método para manejar el registro al hacer clic
    fun onRegisterClicked() {
        saveCommonUserData()
    }

    // Guardar los datos comunes del usuario
    private fun saveCommonUserData() {
        when {
            _password.value != _confirmPassword.value -> {
                _errorMessage.value = "Las contraseñas no coinciden"
            }
            _email.value.isEmpty() || _name.value.isEmpty() || _password.value.isEmpty() -> {
                _errorMessage.value = "Todos los campos son obligatorios"
            }
            else -> {
                viewModelScope.launch {
                    registerUserInFirebase()
                }
            }
        }
    }

    // Autenticación en Firebase y almacenamiento de datos comunes en Firestore
    private fun registerUserInFirebase() {
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserCommonDataToFirestore()
                } else {
                    _errorMessage.value = "Error de autenticación: ${task.exception?.message}"
                }
            }
    }

    // Guardar datos comunes en Firestore
    private fun saveUserCommonDataToFirestore() {
        val userId = auth.currentUser?.uid ?: return
        val userType = if (_isTutor.value) "tutor" else "student"

        val commonUserData = mapOf(
            "name" to _name.value,
            "email" to _email.value,
            "userType" to userType
        )

        firestore.collection("users").document(userId).set(commonUserData)
            .addOnSuccessListener {
                _errorMessage.value = ""
                _isRegistered.value = true
            }
            .addOnFailureListener { e ->
                _errorMessage.value = "Error al registrar el usuario en Firestore: ${e.message}"
            }
    }
}
