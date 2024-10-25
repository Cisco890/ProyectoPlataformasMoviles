import androidx.lifecycle.ViewModel
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.Tutoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomePageTutoresViewModel(
    tutoriasIniciales: List<Tutoria> = emptyList()
) : ViewModel() {

    private val _tutorias = MutableStateFlow(tutoriasIniciales)
    val tutorias: StateFlow<List<Tutoria>> = _tutorias

    init {
        if (tutoriasIniciales.isEmpty()) {
            loadMockTutorias()
        }
    }

    // Función para simular tutorías
    private fun loadMockTutorias() {
        _tutorias.update {
            listOf(
                Tutoria(
                    title = "Ecuaciones diferenciales I",
                    date = "17/09/2024",
                    location = "Presencial: CIT-503",
                    time = "15:00 hrs - 16:00 hrs",
                    link = null
                ),
                Tutoria(
                    title = "Física 3",
                    date = "19/09/2024",
                    location = "Virtual: ",
                    time = "15:00 hrs - 16:00 hrs",
                    link = "Enlace Zoom"
                )
            )
        }
    }
}

