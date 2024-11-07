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

    // Campos específicos para tutores
    private val _year = MutableStateFlow("")
    val year: StateFlow<String> = _year

    private val _hours = MutableStateFlow("")
    val hours: StateFlow<String> = _hours

    private val _selectedCourses = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val selectedCourses: StateFlow<Map<String, Boolean>> = _selectedCourses

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
    fun onYearChanged(newYear: String) { _year.value = newYear }
    fun onHoursChanged(newHours: String) { _hours.value = newHours }

    fun onCourseChecked(course: String, isChecked: Boolean) {
        val updatedCourses = _selectedCourses.value.toMutableMap()
        updatedCourses[course] = isChecked
        _selectedCourses.value = updatedCourses
    }

    fun onRegisterClicked() {
        if (_password.value == _confirmPassword.value && _email.value.isNotEmpty() && _name.value.isNotEmpty()) {
            viewModelScope.launch {
                registerUserInFirebase()
            }
        } else {
            _errorMessage.value = when {
                _password.value != _confirmPassword.value -> "Las contraseñas no coinciden"
                _email.value.isEmpty() || _name.value.isEmpty() || _password.value.isEmpty() -> "Todos los campos son obligatorios"
                else -> "Error en los datos de registro"
            }
        }
    }

    private fun registerUserInFirebase() {
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Registro en Firebase Authentication exitoso") 
                    auth.currentUser?.let {
                        saveUserToFirestore()
                    } ?: run {
                        _errorMessage.value = "Error: usuario no autenticado después del registro"
                    }
                } else {
                    _errorMessage.value = "Error de autenticación: ${task.exception?.message}"
                }
            }
    }

    private fun saveUserToFirestore() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _errorMessage.value = "Error: usuario no autenticado al intentar guardar en Firestore"
            println("Error: usuario no autenticado") // Log para verificar problema
            return
        }

        val userType = if (_isTutor.value) "tutor" else "student"
        val user = mutableMapOf<String, Any>(
            "name" to _name.value,
            "email" to _email.value,
            "userType" to userType
        )

        // Agrega datos específicos si el usuario es tutor
        if (_isTutor.value) {
            user["year"] = _year.value
            user["hours"] = _hours.value
            user["courses"] = _selectedCourses.value.filterValues { it }.keys.toList() // List<String>
        }

        println("Intentando guardar en Firestore: $user") // Log para verificar datos a guardar

        firestore.collection("users").document(userId).set(user)
            .addOnSuccessListener {
                println("Guardado en Firestore exitoso") // Log para éxito
                _errorMessage.value = ""
                _isRegistered.value = true
            }
            .addOnFailureListener { e ->
                _errorMessage.value = "Error al registrar el usuario en Firestore: ${e.message}"
                println("Error al registrar en Firestore: ${e.message}") // Log de error
            }
    }


}
