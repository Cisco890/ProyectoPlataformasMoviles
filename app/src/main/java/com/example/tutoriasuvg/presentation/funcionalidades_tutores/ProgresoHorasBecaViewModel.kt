package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProgresoHorasBecaViewModel(
    private var identifier: String,
    private var isUsingCarnet: Boolean
) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _nombreEstudiante = MutableStateFlow("Nombre desconocido")
    val nombreEstudiante: StateFlow<String> = _nombreEstudiante

    private val _carnetEstudiante = MutableStateFlow("Sin carnet")
    val carnetEstudiante: StateFlow<String> = _carnetEstudiante

    private val _anioEstudio = MutableStateFlow("Año no definido")
    val anioEstudio: StateFlow<String> = _anioEstudio

    private val _horasCompletadas = MutableStateFlow(0)
    val horasCompletadas: StateFlow<Int> = _horasCompletadas

    private val _totalHoras = MutableStateFlow(0)
    val totalHoras: StateFlow<Int> = _totalHoras

    private val _porcentajeProgreso = MutableStateFlow(0f)
    val porcentajeProgreso: StateFlow<Float> = _porcentajeProgreso

    init {
        loadTutorProfileData()
    }

    fun setIdentifier(identifier: String, isUsingCarnet: Boolean) {
        if (this.identifier != identifier || this.isUsingCarnet != isUsingCarnet) {
            this.identifier = identifier
            this.isUsingCarnet = isUsingCarnet
            clearData()
            loadTutorProfileData()
        }
    }

    fun loadTutorProfileData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val querySnapshot = if (isUsingCarnet) {
                    db.collection("users").whereEqualTo("carnet", identifier).get().await()
                } else {
                    db.collection("users").whereEqualTo("email", identifier).get().await()
                }

                if (querySnapshot.documents.isNotEmpty()) {
                    val document = querySnapshot.documents[0]
                    _nombreEstudiante.value = document.getString("name") ?: "Nombre desconocido"
                    _carnetEstudiante.value = document.getString("carnet") ?: "Sin carnet"
                    _anioEstudio.value = document.getString("year") ?: "Año no definido"
                    _horasCompletadas.value = document.getLong("completedHours")?.toInt() ?: 0
                    _totalHoras.value = document.getLong("hours")?.toInt() ?: 0

                    _porcentajeProgreso.value = if (_totalHoras.value > 0) {
                        (_horasCompletadas.value.toFloat() / _totalHoras.value).coerceIn(0f, 1f)
                    } else {
                        0f
                    }
                } else {
                    resetData("Usuario no encontrado")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                resetData("Error al cargar datos")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearData() {
        resetData("Cargando...")
        _isLoading.value = true
    }

    private fun resetData(errorMessage: String) {
        _nombreEstudiante.value = errorMessage
        _carnetEstudiante.value = "N/A"
        _anioEstudio.value = "N/A"
        _horasCompletadas.value = 0
        _totalHoras.value = 0
        _porcentajeProgreso.value = 0f
    }
}
