package com.example.tutoriasuvg.presentation.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterTutorViewModel : ViewModel() {
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
}
