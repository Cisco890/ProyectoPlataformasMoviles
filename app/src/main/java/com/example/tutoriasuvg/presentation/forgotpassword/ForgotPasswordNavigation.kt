package com.example.tutoriasuvg.presentation.forgotpassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ForgotPasswordDestination

fun NavGraphBuilder.forgotPasswordNavigation(
    onBackToLogin: () -> Unit
) {
    composable<ForgotPasswordDestination> {
        ForgotPasswordScreen(
            onBackToLogin = onBackToLogin
        )
    }
}