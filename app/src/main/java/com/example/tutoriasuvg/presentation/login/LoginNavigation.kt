package com.example.tutoriasuvg.presentation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tutoriasuvg.presentation.login.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

fun NavGraphBuilder.loginNavigation(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
){
    composable<LoginDestination>{
        LoginScreen(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateTForgotPassword = onNavigateToForgotPassword
        )
    }
}