package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.model.Solicitud
import com.example.tutoriasuvg.data.repository.TutoriasRepository
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.TutoriasEs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SolicitudView(
    val nombreEstudiante: String,
    val tutoria: String,
    val jornada: String,
    val diasPreferencia: String
)

class HomePageAdminViewModel(
    private val tutoriasRepository: TutoriasRepository = TutoriasRepository()
) : ViewModel() {

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())

    val solicitudes: StateFlow<List<SolicitudView>> = _solicitudes.map { solicitudes ->
        solicitudes.map { solicitud ->
            SolicitudView(
                nombreEstudiante = solicitud.id,
                tutoria = solicitud.courseName,
                jornada = solicitud.shift,
                diasPreferencia = solicitud.days.joinToString(", ")
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadMockSolicitudes()
    }

    private fun loadMockSolicitudes() {
        _solicitudes.value = listOf(
            Solicitud(
                id = "1",
                courseName = "Ecuaciones Diferenciales I",
                days = listOf("Jueves"),
                shift = "Vespertina",
                tutorId = null
            ),
            Solicitud(
                id = "2",
                courseName = "Matemática Discreta I",
                days = listOf("Martes", "Viernes"),
                shift = "Matutina",
                tutorId = null
            )
        )
    }

    fun agregarSolicitud(solicitud: Solicitud) {
        _solicitudes.update { solicitudesActuales ->
            solicitudesActuales + solicitud
        }
    }

    fun actualizarSolicitudes(nuevasSolicitudes: List<Solicitud>) {
        viewModelScope.launch {
            _solicitudes.value = nuevasSolicitudes
        }
    }

    fun asignarTutoria(tutoria: TutoriasEs) {
        tutoriasRepository.agregarTutoria(tutoria)
    }
}
