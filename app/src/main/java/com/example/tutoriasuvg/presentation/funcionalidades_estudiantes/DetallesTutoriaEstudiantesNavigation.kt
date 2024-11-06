package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.DetallesTutoriaEstudiantesScreen
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.TutoriasEs
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// Ruta para navegar con un parámetro JSON que representa el objeto TutoriasEs
const val DetallesTutoriaEstudiantesRoute = "detalles_tutoria_estudiantes/{tutoriaJson}"

// Función para construir la ruta con el objeto TutoriasEs codificado en JSON
fun NavController.navigateToDetallesTutoriaEstudiantes(tutoria: TutoriasEs) {
    val tutoriaJson = Json.encodeToString(tutoria)
    val encodedJson = URLEncoder.encode(tutoriaJson, StandardCharsets.UTF_8.toString())
    this.navigate("detalles_tutoria_estudiantes/$encodedJson")
}

// Configuración de la navegación a la pantalla de detalles del estudiante
fun NavGraphBuilder.DetallesTutoriaEstudiantesNavigation(navController: NavController) {
    composable(
        route = DetallesTutoriaEstudiantesRoute,
        arguments = listOf(navArgument("tutoriaJson") { type = NavType.StringType })
    ) { backStackEntry ->
        val encodedJson = backStackEntry.arguments?.getString("tutoriaJson") ?: return@composable
        val decodedJson = URLDecoder.decode(encodedJson, StandardCharsets.UTF_8.toString())
        val tutoria = Json.decodeFromString<TutoriasEs>(decodedJson)

        DetallesTutoriaEstudiantesScreen(
            onBackClick = { navController.popBackStack() },
            tutoria = tutoria,
            studentName = "Nombre del estudiante",
            isVirtual = tutoria.link != null
        )
    }
}





