package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutoriasuvg.data.model.Solicitud
import com.example.tutoriasuvg.data.model.Tutor
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import com.example.tutoriasuvg.data.repository.TutorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class Notificacion(
    val titulo: String,
    val fecha: String?,
    val modalidad: String,
    val hora: String?,
    val estado: String,
    val tutorId: String? = null,
    val solicitud: Solicitud
)

class NotificacionesViewModel(
    private val solicitudRepository: SolicitudRepository,
    private val tutorRepository: TutorRepository
) : ViewModel() {

    private val _notificaciones = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificaciones: StateFlow<List<Notificacion>> = _notificaciones

    private val _tutoresDisponibles = MutableStateFlow<List<Tutor>>(emptyList())
    val tutoresDisponibles: StateFlow<List<Tutor>> = _tutoresDisponibles

    init {
        cargarNotificaciones()
    }

    fun cargarNotificaciones() {
        viewModelScope.launch {
            solicitudRepository.getAllSolicitudes().collectLatest { solicitudes ->
                val nuevasNotificaciones = solicitudes.map { solicitud ->
                    Notificacion(
                        titulo = solicitud.courseName,
                        fecha = solicitud.date ?: "Fecha a definir",
                        modalidad = solicitud.shift,
                        hora = solicitud.time ?: "Hora a definir",
                        estado = "Pendiente",
                        tutorId = solicitud.tutorId,
                        solicitud = solicitud
                    )
                }
                _notificaciones.value = nuevasNotificaciones
            }
        }
    }

    fun obtenerTutoresParaCurso(courseName: String) {
        viewModelScope.launch {
            val result = tutorRepository.getTutoresPorCurso(courseName)
            _tutoresDisponibles.value = result.getOrElse { emptyList() }
        }
    }

    // Actualizamos esta función para aceptar fecha, ubicación y hora
    fun asignarTutor(solicitud: Solicitud, tutorId: String, date: String?, location: String?, time: String?) {
        viewModelScope.launch {
            solicitudRepository.asignarTutor(
                solicitud = solicitud,
                tutorId = tutorId,
                date = date,
                location = location,
                time = time
            )
            cargarNotificaciones()
        }
    }
}
