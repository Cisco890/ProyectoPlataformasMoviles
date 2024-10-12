package com.example.tutoriasuvg.presentation.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RegisterDestination

fun NavGraphBuilder.registerNavGraph(
    onBackToLogin: () -> Unit
){
    composable<RegisterDestination>{
        RegisterScreen(
            onBackToLogin = onBackToLogin
        )
    }
}