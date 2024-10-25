package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import HomePageTutoresViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomePageTutoresNavigation(navController: NavController) {
    val viewModel = HomePageTutoresViewModel()

    HomePageTutores(
        viewModel = viewModel,
        onTutoriaClick = { tutoria ->
            val tutoriaJson = Json.encodeToString(tutoria)
            val encodedTutoriaJson = URLEncoder.encode(tutoriaJson, StandardCharsets.UTF_8.toString())
            navController.navigate("detalles_tutoria/$encodedTutoriaJson")
        },
        onProgresoClick = {
            navController.navigate("progresoHorasBeca")
        }
    )
}
