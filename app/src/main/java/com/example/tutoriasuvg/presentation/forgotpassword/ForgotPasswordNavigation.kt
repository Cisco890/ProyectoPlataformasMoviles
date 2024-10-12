package com.example.tutoriasuvg.presentation.forgotpassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ForgotPasswordNavigation

fun NavGraphBuilder.forgotPasswordNavGraph(
    onBackToLogin: () -> Unit
) {
    composable<ForgotPasswordNavigation> {
        ForgotPasswordScreen(
            onBackToLogin = onBackToLogin
        )
    }
}