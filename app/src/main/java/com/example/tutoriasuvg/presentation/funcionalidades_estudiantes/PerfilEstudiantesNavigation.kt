package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository

@Composable
fun PerfilEstudianteNavigation(
    navController: NavController,
    loginRepository: FirebaseLoginRepository,
    registerRepository: FirebaseRegisterRepository
) {
    val viewModel: PerfilEstudianteViewModel = viewModel(
        factory = PerfilEstudianteViewModelFactory(loginRepository, registerRepository)
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
            val hours = 10
            val courses = listOf("Matemáticas", "Física")
            viewModel.volvermeTutor(
                hours = hours,
                courses = courses,
                onSuccess = { navController.navigate("register_tutor_screen") },
                onFailure = { errorMessage -> /* Manejar el error */ }
            )
        },
        viewModel = viewModel
    )
}
