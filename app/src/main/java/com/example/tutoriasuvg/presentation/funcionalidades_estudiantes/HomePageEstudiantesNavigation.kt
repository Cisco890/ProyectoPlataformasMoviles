package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomePageEstudiantesNavigation(navController: NavController, solicitudRepository: SolicitudRepository) {
    val viewModel: HomePageEstudiantesViewModel = viewModel(
        factory = HomePageEstudiantesViewModelFactory(solicitudRepository)
    )

    HomePageEstudiantes(
        viewModel = viewModel,
        onTutoriaEstudianteClick = { tutoriasEs ->
            val tutoriasEsJson = Json.encodeToString(tutoriasEs)
            val encodedTutoriasEsJson = URLEncoder.encode(tutoriasEsJson, StandardCharsets.UTF_8.toString())
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
