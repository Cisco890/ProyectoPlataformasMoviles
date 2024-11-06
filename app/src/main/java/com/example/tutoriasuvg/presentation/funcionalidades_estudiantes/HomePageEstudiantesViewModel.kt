package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class TutoriasEs(
    val title: String,
    val date: String,
    val location: String,
    val time: String,
    val link: String?
)

class HomePageEstudiantesViewModel(
    tutoriasIniciales: List<TutoriasEs> = emptyList()  // Cambiado a TutoriasEs
) : ViewModel() {

    private val _tutorias = MutableStateFlow(tutoriasIniciales)
    val tutorias: StateFlow<List<TutoriasEs>> = _tutorias  // Cambiado a TutoriasEs

    private val _hasTutorias = MutableStateFlow(tutoriasIniciales.isNotEmpty())
    val hasTutorias: StateFlow<Boolean> = _hasTutorias

    init {
        if (tutoriasIniciales.isEmpty()) {
            loadMockTutorias()
        }
    }

    private fun loadMockTutorias() {
        viewModelScope.launch {
            _tutorias.update {
                listOf(
                    TutoriasEs(
                        title = "Ecuaciones diferenciales I",
                        date = "17/09/2024",
                        location = "Presencial: CIT-503",
                        time = "15:00 hrs - 16:00 hrs",
                        link = null
                    ),
                    TutoriasEs(
                        title = "Física 3",
                        date = "19/09/2024",
                        location = "Virtual: ",
                        time = "15:00 hrs - 16:00 hrs",
                        link = "Enlace Zoom"
                    )
                )
            }
            _hasTutorias.value = true
        }
    }
}
