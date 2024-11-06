package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomePageEstudiantesNavigation(navController: NavController) {
    val viewModel = HomePageEstudiantesViewModel()

    HomePageEstudiantes(
        viewModel = viewModel,
        onTutoriaEstudianteClick = { tutoriasEs ->
            val tutoriasEsJson = Json.encodeToString(tutoriasEs)
            Log.d("HomePageEstudiantes", "Encoded JSON: $tutoriasEsJson")

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

