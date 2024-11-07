package com.example.tutoriasuvg.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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
        _isLoading.value = true
        _errorMessage.value = ""

        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        checkUserType(userId, onLoginSuccess)
                    } else {
                        _errorMessage.value = "Error de autenticación. Intente nuevamente."
                    }
                } else {
                    _errorMessage.value = "Correo o contraseña incorrectos."
                }
            }
    }

    private fun checkUserType(userId: String, onLoginSuccess: (String) -> Unit) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val userType = document.getString("userType") ?: ""
                when (userType) {
                    "student" -> onLoginSuccess("student")
                    "tutor" -> onLoginSuccess("tutor")
                    "admin" -> onLoginSuccess("admin")
                    else -> _errorMessage.value = "Tipo de usuario no válido."
                }
            }
            .addOnFailureListener {
                _errorMessage.value = "Error al obtener los datos de usuario."
            }
    }
}
