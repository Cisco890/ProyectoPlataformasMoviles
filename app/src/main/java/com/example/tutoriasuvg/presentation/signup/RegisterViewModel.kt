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

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _isTutor = MutableStateFlow(false) // Añadimos isTutor para el checkbox
    val isTutor: StateFlow<Boolean> = _isTutor

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered

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

    fun onTutorChecked(isChecked: Boolean) {
        _isTutor.value = isChecked
    }

    fun onRegisterClicked() {
        if (_password.value == _confirmPassword.value && _email.value.isNotEmpty() && _name.value.isNotEmpty()) {
            viewModelScope.launch {
                if (_isTutor.value) {
                    // La navegación a la pantalla de registro de tutor se maneja en la UI
                    // Este bloque permanece vacío intencionadamente
                } else {
                    registerStudentInFirebase() // Registro básico para estudiantes
                }
            }
        } else {
            _errorMessage.value = when {
                _password.value != _confirmPassword.value -> "Las contraseñas no coinciden"
                _email.value.isEmpty() || _name.value.isEmpty() || _password.value.isEmpty() -> "Todos los campos son obligatorios"
                else -> "Error en los datos de registro"
            }
        }
    }

    private fun registerStudentInFirebase() {
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveStudentToFirestore()
                } else {
                    _errorMessage.value = "Error de autenticación: ${task.exception?.message}"
                }
            }
    }

    private fun saveStudentToFirestore() {
        val userId = auth.currentUser?.uid ?: return
        val user = hashMapOf(
            "name" to _name.value,
            "email" to _email.value,
            "userType" to "student"
        )

        firestore.collection("users").document(userId).set(user)
            .addOnSuccessListener {
                _errorMessage.value = ""
                _isRegistered.value = true
            }
            .addOnFailureListener { e ->
                _errorMessage.value = "Error al registrar el usuario en Firestore: ${e.message}"
            }
    }
}
