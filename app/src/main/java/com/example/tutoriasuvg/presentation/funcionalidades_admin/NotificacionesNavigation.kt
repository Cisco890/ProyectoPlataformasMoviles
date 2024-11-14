package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import com.example.tutoriasuvg.data.repository.TutorRepository
import kotlinx.serialization.Serializable

@Serializable
data class NotificacionesDestination(val route: String = "notificaciones")

@Composable
fun NotificacionesNavigation(
    navController: NavController,
    solicitudRepository: SolicitudRepository,
    tutorRepository: TutorRepository
) {
    val viewModel: NotificacionesViewModel = viewModel(
        factory = NotificacionesViewModelFactory(
            solicitudRepository = solicitudRepository,
            tutorRepository = tutorRepository
        )
    )

    NotificacionesScreen(
        viewModel = viewModel,
        onBackClick = { navController.popBackStack() }
    )
}
