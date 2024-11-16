package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.repository.TutoriaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetallesTutoriaEstudiantesViewModel(
    private val tutoriaRepository: TutoriaRepository
) : ViewModel() {

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun completarTutoria(
        solicitudId: String,
        tutoriaAsignadaId: Int,
        tutorId: String,
        incrementoHoras: Float = 1.3f
    ) {
        viewModelScope.launch {
            try {
                println("Iniciando completarTutoria con solicitudId=$solicitudId, tutoriaAsignadaId=$tutoriaAsignadaId, tutorId=$tutorId")
                tutoriaRepository.completarTutoria(
                    solicitudId = solicitudId,
                    tutoriaAsignadaId = tutoriaAsignadaId,
                    tutorId = tutorId,
                    incrementoHoras = incrementoHoras
                )
                println("completarTutoria ejecutado exitosamente")
                _isCompleted.value = true
            } catch (e: Exception) {
                println("Error en completarTutoria: ${e.message}")
                _errorMessage.value = e.message
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
