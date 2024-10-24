package com.example.tutoriasuvg.presentation.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RegisterTutorDestination {
    const val route = "register_tutor_screen"
}

fun NavGraphBuilder.registerTutorNavigation(
    onBackToLogin: () -> Unit
) {
    composable(route = RegisterTutorDestination.route) {
        RegisterStudentTutorScreen(
            onBackToLogin = onBackToLogin
        )
    }
}
