package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data class NotificacionesDestination(val route: String = "notificaciones")

@Composable
fun NotificacionesNavigation(navController: NavController) {
    NotificacionesScreen(
        onBackClick = { navController.popBackStack() }
    )
}
