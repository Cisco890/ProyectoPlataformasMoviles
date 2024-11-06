package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SolicitudTutoriaViewModel : ViewModel() {

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
            currentShifts.mapValues { (key, _) -> key == shift }
        }
    }

    fun solicitarTutoria() {
 // se debe agregar la lógica para que la api funcione y se pueda guardar los datos
    }
}
