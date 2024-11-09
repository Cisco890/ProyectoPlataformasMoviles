package com.example.tutoriasuvg.presentation.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
import kotlinx.serialization.Serializable

@Serializable
data object RegisterTutorDestination {
    const val route = "register_tutor_screen"
}

fun NavGraphBuilder.registerTutorNavigation(
    navController: NavController,
    registerRepository: FirebaseRegisterRepository
) {
    composable(route = RegisterTutorDestination.route) {
        RegisterStudentTutorScreen(
            onBackToLogin = {
                navController.navigate("login_screen") {
                    popUpTo(0)
                    launchSingleTop = true
                }
            },
            registerRepository = registerRepository
        )
    }
}
