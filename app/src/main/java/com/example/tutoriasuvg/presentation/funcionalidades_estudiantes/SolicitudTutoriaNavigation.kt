package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.repository.SolicitudRepository

@Composable
fun SolicitudTutoriaNavigation(
    navController: NavController,
    solicitudRepository: SolicitudRepository
) {
    val factory = SolicitudTutoriaViewModelFactory(solicitudRepository)
    val viewModel: SolicitudTutoriaViewModel = viewModel(factory = factory)

    SolicitudTutoriaScreen(
        viewModel = viewModel,
        navController = navController,
        onBackClick = { navController.popBackStack() }
    )
}
