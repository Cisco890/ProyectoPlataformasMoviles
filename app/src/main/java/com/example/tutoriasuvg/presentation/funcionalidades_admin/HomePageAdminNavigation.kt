package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import com.example.tutoriasuvg.data.repository.TutorRepository
import com.example.tutoriasuvg.presentation.funcionalidades_admin.HomePageAdmin

@Composable
fun HomePageAdminNavigation(
    navController: NavController,
    solicitudRepository: SolicitudRepository,
    tutorRepository: TutorRepository
) {
    val viewModel = NotificacionesViewModel(solicitudRepository, tutorRepository)

    HomePageAdmin(
        viewModel = viewModel,
        onProfileClick = {
            navController.navigate(PerfilAdminDestination().route)
        }
    )
}
