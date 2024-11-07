package com.example.tutoriasuvg.presentation.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterTutorViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _year = MutableStateFlow("")
    val year: StateFlow<String> = _year

    private val _hours = MutableStateFlow("")
    val hours: StateFlow<String> = _hours

    val courses = listOf(
        "Pensamiento Cuantitativo", "Ciencias de la Vida", "Comunicación Efectiva",
        "Química General", "Algoritmos y Programación Básica", "Cálculo 1",
        "Física 1", "Álgebra Lineal", "Estadística 1", "Ciudadanía Global",
        "Programación Orientada a Objetos", "Cálculo 2", "Física 2",
        "Algoritmos y Estructuras de Datos", "Organización de Computadoras y Assambler",
        "Guatemala en el Contexto Mundial", "Retos Ambientales y Sostenibilidad",
        "Física 3", "Ecuaciones Diferenciales 1", "Investigación y Pensamiento Cuantitativo",
        "Matemática Discreta 1", "Programación de Microprocesadores",
        "Programación de Aplicaciones Móviles"
    )

    private val _selectedCourses = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val selectedCourses: StateFlow<Map<String, Boolean>> = _selectedCourses

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered

    fun onYearChanged(newYear: String) {
        _year.value = newYear
    }

    fun onHoursChanged(newHours: String) {
        _hours.value = newHours
    }

    fun onCourseChecked(course: String, isChecked: Boolean) {
        val updatedCourses = _selectedCourses.value.toMutableMap()
        updatedCourses[course] = isChecked
        _selectedCourses.value = updatedCourses
    }

    private fun saveTutorToFirestore() {
        val userId = auth.currentUser?.uid ?: return
        val selectedCoursesList = _selectedCourses.value.filterValues { it }.keys.toList()

        val tutorData = hashMapOf(
            "year" to _year.value,
            "hours" to _hours.value,
            "courses" to selectedCoursesList,
            "userType" to "tutor"
        )

        firestore.collection("users").document(userId).update(tutorData)
            .addOnSuccessListener {
                _errorMessage.value = ""
                _isRegistered.value = true
            }
            .addOnFailureListener { e ->
                _errorMessage.value = "Error al registrar datos del tutor en Firestore: ${e.message}"
            }
    }
}
