package com.example.tutoriasuvg.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterTutorViewModel(
    private val registerRepository: FirebaseRegisterRepository
) : ViewModel() {

    val courses = listOf(
        "Pensamiento Cuantitativo", "Ciencias de la Vida", "Comunicación Efectiva",
        "Química General", "Algoritmos y Programación Básica", "Cálculo 1",
        "Física 1", "Álgebra Lineal", "Estadística 1", "Ciudadanía Global",
        "Programación Orientada a Objetos", "Cálculo 2", "Física 2",
        "Algoritmos y Estructuras de Datos", "Organización de Computadoras y Assembler",
        "Guatemala en el Contexto Mundial", "Retos Ambientales y Sostenibilidad",
        "Física 3", "Ecuaciones Diferenciales 1", "Investigación y Pensamiento Cuantitativo",
        "Matemática Discreta 1", "Programación de Microprocesadores",
        "Programación de Aplicaciones Móviles"
    )

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

    fun registerTutor(userId: String) {
        val selectedCoursesList = _selectedCourses.value.filterValues { it }.keys.toList()
        viewModelScope.launch {
            val result = registerRepository.registerTutorData(userId, _year.value, _hours.value, selectedCoursesList)
            result.fold(
                onSuccess = {
                    _isRegistered.value = true
                },
                onFailure = { exception ->
                    _errorMessage.value = "Error al registrar el tutor: ${exception.message}"
                }
            )
        }
    }
}
