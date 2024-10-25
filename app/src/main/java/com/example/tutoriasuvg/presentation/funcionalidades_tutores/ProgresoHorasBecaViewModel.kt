import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProgresoHorasBecaViewModel : ViewModel() {

    private val _nombreEstudiante = MutableStateFlow("Juan Perez")
    val nombreEstudiante: StateFlow<String> = _nombreEstudiante

    private val _horasCompletadas = MutableStateFlow(30)
    val horasCompletadas: StateFlow<Int> = _horasCompletadas

    private val _totalHoras = MutableStateFlow(40)
    val totalHoras: StateFlow<Int> = _totalHoras

    private val _porcentajeProgreso = MutableStateFlow(0.75f)
    val porcentajeProgreso: StateFlow<Float> = _porcentajeProgreso
}

