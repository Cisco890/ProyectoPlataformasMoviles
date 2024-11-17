import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.DetalleTutoria
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.Tutoria
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DetallesTutoriaNavigation(
    navController: NavController,
    tutoriaJson: String,
    sessionManager: SessionManager
) {
    val decodedTutoriaJson = URLDecoder.decode(tutoriaJson, StandardCharsets.UTF_8.toString())
    val tutoria = Json.decodeFromString<Tutoria>(decodedTutoriaJson)

    var userId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        userId = sessionManager.getUserIdentifierSync()
    }

    if (userId != null) {
        DetalleTutoria(
            onBackClick = { navController.popBackStack() },
            title = tutoria.title,
            date = tutoria.date,
            location = tutoria.location,
            time = tutoria.time,
            studentName = "Nombre del Estudiante",
            isVirtual = tutoria.link != null,
            link = tutoria.link,
            userId = userId!!
        )
    }
}
