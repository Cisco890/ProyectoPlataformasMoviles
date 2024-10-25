package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

open class HomePageAdminViewModel : ViewModel() {
    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    open val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    init {
        loadMockSolicitudes()
    }

    private fun loadMockSolicitudes() {
        _solicitudes.update {
            listOf(
                Solicitud("FERNANDO JOSÉ RUEDA RODAS", "Ecuaciones Diferenciales I", "Vespertina", "Jueves"),
                Solicitud("FERNANDO ANDREE HERNÁNDEZ", "Matemática Discreta I", "Matutina", "Martes, Viernes")
            )
        }
    }
}
