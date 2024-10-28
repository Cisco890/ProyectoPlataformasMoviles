package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NotificacionesNavigation(navController: NavController) {
    NotificacionesScreen(
        onBackClick = { navController.popBackStack() }
    )
}
