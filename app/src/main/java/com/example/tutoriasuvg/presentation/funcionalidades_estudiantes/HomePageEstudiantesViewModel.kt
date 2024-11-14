package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.Serializable

@Serializable
data class TutoriasEs(
    val title: String,
    val date: String? = "Fecha a definir",
    val location: String? = "Ubicación a definir",
    val time: String? = "Hora a definir",
    val link: String? = null
)


class HomePageEstudiantesViewModel(
    private val solicitudRepository: SolicitudRepository
) : ViewModel() {

    val tutorias: StateFlow<List<TutoriasEs>> = solicitudRepository.getAsignacionesParaEstudiante()
        .map { solicitudes ->
            solicitudes.map { solicitud ->
                TutoriasEs(
                    title = solicitud.courseName,
                    date = solicitud.date ?: "Fecha a definir",
                    location = solicitud.location ?: "Ubicación a definir",
                    time = solicitud.time ?: "Hora a definir",
                    link = solicitud.link
                )
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

