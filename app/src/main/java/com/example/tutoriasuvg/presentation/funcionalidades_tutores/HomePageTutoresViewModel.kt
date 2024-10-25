import androidx.lifecycle.ViewModel
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.Tutoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomePageTutoresViewModel(
    tutoriasIniciales: List<Tutoria> = emptyList()
) : ViewModel() {

    private val _tutorias = MutableStateFlow(tutoriasIniciales)
    val tutorias: StateFlow<List<Tutoria>> = _tutorias
}

