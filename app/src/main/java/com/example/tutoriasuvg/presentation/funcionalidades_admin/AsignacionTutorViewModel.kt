package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.model.Solicitud
import com.example.tutoriasuvg.data.model.Tutor
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import com.example.tutoriasuvg.data.repository.TutorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AsignacionTutorViewModel(
    private val solicitudRepository: SolicitudRepository,
    private val tutorRepository: TutorRepository
) : ViewModel() {

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    private val _tutores = MutableStateFlow<List<Tutor>>(emptyList())
    val tutores: StateFlow<List<Tutor>> = _tutores

    fun cargarSolicitudesPendientes() {
        viewModelScope.launch {
            val result = solicitudRepository.getAllSolicitudes().first()
            _solicitudes.value = result
        }
    }

    fun cargarTutoresPorCurso(courseName: String) {
        viewModelScope.launch {
            val result = tutorRepository.getTutoresPorCurso(courseName)
            if (result.isSuccess) {
                _tutores.value = result.getOrDefault(emptyList())
            }
        }
    }

    fun asignarTutor(solicitud: Solicitud, tutorId: String) {
        viewModelScope.launch {
            solicitudRepository.asignarTutor(solicitud, tutorId)
            cargarSolicitudesPendientes()
        }
    }
}
