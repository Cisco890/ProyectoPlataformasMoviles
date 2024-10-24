package com.example.tutoriasuvg.presentation.forgotpassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ForgotPasswordDestination {
    const val route = "forgot_password_screen"
}

fun NavGraphBuilder.forgotPasswordNavigation(
    onBackToLogin: () -> Unit
) {
    composable(route = ForgotPasswordDestination.route) {
        ForgotPasswordScreen(
            onBackToLogin = onBackToLogin
        )
    }
}
