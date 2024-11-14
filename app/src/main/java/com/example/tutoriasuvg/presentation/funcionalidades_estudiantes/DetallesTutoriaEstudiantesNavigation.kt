package com.example.tutoriasuvg.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutoriasuvg.data.repository.SolicitudRepository // Asegúrate de importar esta clase
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.DetallesTutoriaEstudiantesScreen
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.TutoriasEs
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val DetallesTutoriaEstudiantesRoute = "detalles_tutoria_estudiantes/{tutoriaJson}"

fun NavController.navigateToDetallesTutoriaEstudiantes(tutoria: TutoriasEs) {
    val tutoriaJson = Json.encodeToString(tutoria)
    val encodedJson = URLEncoder.encode(tutoriaJson, StandardCharsets.UTF_8.toString())
    this.navigate("detalles_tutoria_estudiantes/$encodedJson")
}

fun NavGraphBuilder.DetallesTutoriaEstudiantesNavigation(
    navController: NavController,
    tutorId: String,
    solicitudRepository: SolicitudRepository
) {
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
            isVirtual = tutoria.link != null,
            tutorId = tutorId,
            solicitudRepository = solicitudRepository
        )
    }
}
