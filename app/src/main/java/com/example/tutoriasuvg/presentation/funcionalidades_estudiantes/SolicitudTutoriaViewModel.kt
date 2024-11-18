package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SolicitudTutoriaViewModel(
    private val solicitudRepository: SolicitudRepository,
    private val studentId: String
) : ViewModel() {

    private val _courseName = MutableStateFlow("")
    val courseName: StateFlow<String> = _courseName

    private val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
    private val _selectedDays = MutableStateFlow(
        daysOfWeek.associateWith { false }
    )
    val selectedDays: StateFlow<Map<String, Boolean>> = _selectedDays

    private val shifts = listOf("Matutina", "Vespertina")
    private val _selectedShift = MutableStateFlow(
        shifts.associateWith { false }
    )
    val selectedShift: StateFlow<Map<String, Boolean>> = _selectedShift

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun updateCourseName(newName: String) {
        _courseName.update { newName }
    }

    fun toggleDay(day: String) {
        _selectedDays.update { currentDays ->
            currentDays.mapValues { (key, value) ->
                if (key == day) !value else value
            }
        }
    }

    fun selectShift(shift: String) {
        _selectedShift.update { currentShifts ->
            currentShifts.mapValues { (key, _) ->
                key == shift
            }
        }
    }

    fun solicitarTutoria() {
        val selectedDaysList = _selectedDays.value.filterValues { it }.keys.toList()
        val selectedShift = _selectedShift.value.filterValues { it }.keys.firstOrNull()

        if (_courseName.value.isBlank() || selectedDaysList.isEmpty() || selectedShift == null) {
            _message.value = "Por favor, complete todos los campos"
            return
        }

        viewModelScope.launch {
            try {
                solicitudRepository.enviarSolicitudTutor(
                    courseName = _courseName.value,
                    days = selectedDaysList,
                    shift = selectedShift,
                    studentId = studentId
                )
                _message.value = "Solicitud enviada exitosamente"
                clearForm()
            } catch (e: Exception) {
                _message.value = "Error al enviar la solicitud: ${e.message}"
            }
        }
    }

    private fun clearForm() {
        _courseName.value = ""
        _selectedDays.update { it.mapValues { false } }
        _selectedShift.update { it.mapValues { false } }
    }

    fun clearMessage() {
        _message.update { null }
    }
}
