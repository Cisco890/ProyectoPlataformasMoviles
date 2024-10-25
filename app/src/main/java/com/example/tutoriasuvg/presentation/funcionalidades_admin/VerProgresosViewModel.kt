package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class Progreso(
    val nombreTutor: String,
    val horasRealizadas: String
)

class VerProgresosViewModel : ViewModel() {

    private val _progresos = MutableStateFlow<List<Progreso>>(emptyList())
    val progresos: StateFlow<List<Progreso>> = _progresos

    init {
        loadMockProgresos()
    }

    private fun loadMockProgresos() {
        _progresos.update {
            listOf(
                Progreso("Tutor 1", "12 horas"),
                Progreso("Tutor 2", "4 horas")
            )
        }
    }
}
