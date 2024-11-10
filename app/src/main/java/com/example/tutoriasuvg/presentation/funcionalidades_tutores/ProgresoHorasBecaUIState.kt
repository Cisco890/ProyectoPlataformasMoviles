package com.example.tutoriasuvg.presentation.funcionalidades_tutores

data class ProgresoHorasBecaUIState(
    val isLoading: Boolean = true,
    val nombreEstudiante: String = "Nombre desconocido",
    val carnetEstudiante: String = "Sin carnet",
    val anioEstudio: String = "Año no definido",
    val horasCompletadas: Int = 0,
    val totalHoras: Int = 0,
    val porcentajeProgreso: Float = 0f,
    val isLoggingOut: Boolean = false,
    val errorMessage: String? = null
)
