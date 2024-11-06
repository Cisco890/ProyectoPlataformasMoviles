package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.CommonAppBar

@Composable
fun SolicitudTutoriaNavigation(
    navController: NavController,
    viewModel: SolicitudTutoriaViewModel = viewModel() // Provide ViewModel instance
) {
    SolicitudTutoriaScreen(
        onBackClick = { navController.popBackStack() }, // Define back navigation
        viewModel = viewModel
    )
}
