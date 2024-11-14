package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.model.TutoriaAsignada
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TutorAsignacionesViewModel(
    private val solicitudRepository: SolicitudRepository
) : ViewModel() {

    fun obtenerAsignacionesParaTutor(tutorId: String): StateFlow<List<TutoriaAsignada>> {
        return solicitudRepository.getAsignacionesParaTutor(tutorId)
            .map { solicitudes ->
                solicitudes.map { solicitud ->
                    TutoriaAsignada(
                        courseName = solicitud.courseName,
                        date = solicitud.date ?: "Fecha a definir",
                        time = solicitud.time ?: "Hora a definir",
                        modalidad = solicitud.shift,
                        location = solicitud.location ?: "Ubicación a definir"
                    )
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
}
