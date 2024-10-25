package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class Notificacion(
    val titulo: String,
    val fecha: String,
    val modalidad: String,
    val hora: String,
    val estado: String
)

class NotificacionesViewModel : ViewModel() {

    private val _notificaciones = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificaciones: StateFlow<List<Notificacion>> = _notificaciones

    init {
        loadMockNotificaciones()
    }

    private fun loadMockNotificaciones() {
        _notificaciones.update {
            listOf(
                Notificacion("Ecuaciones diferenciales I", "17/09/2024", "Presencial: CIT-503", "15:00 hrs - 16:00 hrs", "¡Finalizada!"),
                Notificacion("Física 3", "19/09/2024", "Virtual: Enlace Zoom", "15:00 hrs - 16:00 hrs", "Pendiente")
            )
        }
    }
}
