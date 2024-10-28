package com.example.tutoriasuvg.presentation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination {
    const val route = "login_screen"
}

fun NavGraphBuilder.loginNavigation(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginAsUser: () -> Unit,
    onLoginAsTutor: () -> Unit,
    onLoginAsAdmin: () -> Unit
) {
    composable(route = LoginDestination.route) {
        LoginScreen(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateTForgotPassword = onNavigateToForgotPassword,
            onLoginAsUser = onLoginAsUser,
            onLoginAsTutor = onLoginAsTutor,
            onLoginAsAdmin = onLoginAsAdmin
        )
    }
}

