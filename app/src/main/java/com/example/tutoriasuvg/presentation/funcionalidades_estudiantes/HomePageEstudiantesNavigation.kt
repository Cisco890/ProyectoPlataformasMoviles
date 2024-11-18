package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomePageEstudiantesNavigation(
    navController: NavController,
    solicitudRepository: SolicitudRepository,
    sessionManager: SessionManager
) {
    var studentId by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            studentId = sessionManager.getUserIdentifierSync()
        } catch (e: Exception) {
            isError = true
        }
    }

    when {
        isError -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error al cargar el identificador del estudiante.")
            }
        }

        studentId == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            val viewModel: HomePageEstudiantesViewModel = viewModel(
                factory = HomePageEstudiantesViewModelFactory(
                    solicitudRepository = solicitudRepository,
                    studentId = studentId!!
                )
            )

            HomePageEstudiantes(
                viewModel = viewModel,
                onTutoriaEstudianteClick = { tutoriasEs ->
                    val tutoriasEsJson = Json.encodeToString(tutoriasEs)
                    val encodedTutoriasEsJson =
                        URLEncoder.encode(tutoriasEsJson, StandardCharsets.UTF_8.toString())
                    navController.navigate("detallesTutoriaEstudiantes/$encodedTutoriasEsJson")
                },
                onSolicitudTutoriaClick = {
                    navController.navigate("solicitud_tutoria")
                },
                onPerfilClick = {
                    navController.navigate("perfil_estudiante")
                }
            )
        }
    }
}
