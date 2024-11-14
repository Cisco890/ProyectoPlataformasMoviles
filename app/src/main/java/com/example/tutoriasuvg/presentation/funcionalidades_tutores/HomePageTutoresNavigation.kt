package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomePageTutoresNavigation(
    navController: NavController,
    solicitudRepository: SolicitudRepository,
    tutorId: String
) {
    val viewModel = HomePageTutoresViewModel(
        solicitudRepository = solicitudRepository,
        tutorId = tutorId
    )

    HomePageTutores(
        viewModel = viewModel,
        onTutoriaClick = { tutoria ->
            try {
                val tutoriaJson = Json.encodeToString(tutoria)
                val encodedTutoriaJson = URLEncoder.encode(tutoriaJson, "UTF-8")
                navController.navigate("detalles_tutoria/$encodedTutoriaJson")
            } catch (e: Exception) {
                Log.e("NavigationError", "Error encoding tutoria JSON", e)
            }
        },
        onProgresoClick = {
            navController.navigate("perfil_tutor")
        }
    )
}

