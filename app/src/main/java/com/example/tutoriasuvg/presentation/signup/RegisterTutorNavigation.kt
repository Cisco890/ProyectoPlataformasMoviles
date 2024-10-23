package com.example.tutoriasuvg.presentation.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RegisterTutorDestination

fun NavGraphBuilder.registerTutorNavigation(
    onBackToLogin: () -> Unit
){
    composable<RegisterDestination>{
        RegisterStudentTutorScreen(
            onBackToLogin = onBackToLogin
        )
    }
}