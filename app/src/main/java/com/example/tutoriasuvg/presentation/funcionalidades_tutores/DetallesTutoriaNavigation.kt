package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DetallesTutoriaNavigation(navController: NavController, tutoriaJson: String) {
    val decodedTutoriaJson = URLDecoder.decode(tutoriaJson, StandardCharsets.UTF_8.toString())
    val tutoria = Json.decodeFromString<Tutoria>(decodedTutoriaJson)

    DetalleTutoria(
        onBackClick = { navController.popBackStack() },
        title = tutoria.title,
        date = tutoria.date,
        location = tutoria.location,
        time = tutoria.time,
        studentName = "Nombre del Estudiante",
        isVirtual = tutoria.link != null,
        link = tutoria.link
    )
}
