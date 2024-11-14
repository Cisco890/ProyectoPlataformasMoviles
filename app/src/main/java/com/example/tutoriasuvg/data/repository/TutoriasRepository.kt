package com.example.tutoriasuvg.data.repository

import com.example.tutoriasuvg.data.model.Solicitud
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.TutoriasEs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TutoriasRepository {

    private val _tutoriasEstudiantes = MutableStateFlow<List<TutoriasEs>>(emptyList())
    val tutoriasEstudiantes: StateFlow<List<TutoriasEs>> = _tutoriasEstudiantes

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    fun agregarTutoria(tutoria: TutoriasEs) {
        _tutoriasEstudiantes.value = _tutoriasEstudiantes.value + tutoria
    }

    fun obtenerTutoriasEstudiantes(): Flow<List<TutoriasEs>> = tutoriasEstudiantes

    fun actualizarSolicitudes(nuevasSolicitudes: List<Solicitud>) {
        _solicitudes.value = nuevasSolicitudes
    }

    fun obtenerSolicitudes(): Flow<List<Solicitud>> = _solicitudes
}
