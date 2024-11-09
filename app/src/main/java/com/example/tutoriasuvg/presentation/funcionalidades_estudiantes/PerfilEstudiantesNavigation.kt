package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository

@Composable
fun PerfilEstudianteNavigation(
    navController: NavController,
    loginRepository: FirebaseLoginRepository
) {
    val viewModel: PerfilEstudianteViewModel = viewModel(
        factory = PerfilEstudianteViewModelFactory(loginRepository)
    )

    PerfilEstudianteScreen(
        onBackClick = { navController.popBackStack() },
        onLogoutClick = {
            viewModel.logout()
            navController.navigate("login_screen") {
                popUpTo("perfil") { inclusive = true }
            }
        },
        onBecomeTutorClick = {
            viewModel.volvermeTutor()
            navController.navigate("register_tutor_screen")
        },
        viewModel = viewModel
    )
}
