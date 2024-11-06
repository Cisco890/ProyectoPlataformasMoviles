package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun PerfilEstudianteNavigation(
    navController: NavController,
    viewModel: PerfilEstudianteViewModel = viewModel() // ViewModel instance for PerfilEstudianteScreen
) {
    PerfilEstudianteScreen(
        onBackClick = { navController.popBackStack() }, // Handle back navigation
        onLogoutClick = { navController.navigate("register_screen") { // Navigate to LoginScreen
            popUpTo("perfil") { inclusive = true } // Remove PerfilEstudianteScreen from back stack
        }},
        onBecomeTutorClick = {
            viewModel.volvermeTutor()
            navController.navigate("register_tutor_screen") // Navigate to RegisterTutorScreen
        },
        viewModel = viewModel
    )
}
