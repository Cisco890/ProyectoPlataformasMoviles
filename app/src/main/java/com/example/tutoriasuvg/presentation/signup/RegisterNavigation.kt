package com.example.tutoriasuvg.presentation.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
import kotlinx.serialization.Serializable

@Serializable
data object RegisterDestination {
    const val route = "register_screen"
}

fun NavGraphBuilder.registerNavigation(
    onBackToLogin: () -> Unit,
    onNavigateToRegisterTutor: () -> Unit,
    registerRepository: FirebaseRegisterRepository
) {
    composable(route = RegisterDestination.route) {
        RegisterScreen(
            onBackToLogin = onBackToLogin,
            onNavigateToRegisterTutor = onNavigateToRegisterTutor,
            registerRepository = registerRepository
        )
    }
}
