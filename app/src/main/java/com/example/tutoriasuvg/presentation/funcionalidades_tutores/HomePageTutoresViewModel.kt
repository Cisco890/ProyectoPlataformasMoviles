package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.Serializable

@Serializable
data class Tutoria(
    val id: String,
    val title: String,
    val date: String?,
    val location: String?,
    val time: String?,
    val link: String?
)

class HomePageTutoresViewModel(
    private val solicitudRepository: SolicitudRepository,
    tutorId: String
) : ViewModel() {

    val tutorias: StateFlow<List<Tutoria>> = solicitudRepository.getAsignacionesParaTutor(tutorId)
        .map { solicitudes ->
            solicitudes.map { solicitud ->
                Tutoria(
                    id = solicitud.id,
                    title = solicitud.courseName,
                    date = solicitud.date ?: "Fecha a definir",
                    location = solicitud.location ?: "Ubicación a definir",
                    time = solicitud.time ?: "Hora a definir",
                    link = solicitud.link
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

