package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomePageAdminNavigation(navController: NavController) {
    HomePageAdmin(
        onVerProgresosClick = {
            navController.navigate(VerProgresosDestination().route)
        },
        onNotificacionesClick = {
            navController.navigate(NotificacionesDestination().route)
        }
    )
}
