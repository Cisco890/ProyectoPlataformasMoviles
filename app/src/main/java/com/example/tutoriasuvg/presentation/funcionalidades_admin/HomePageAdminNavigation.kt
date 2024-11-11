package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data class HomePageAdminDestination(val route: String = "homePageAdmin")

@Composable
fun HomePageAdminNavigation(navController: NavController) {
    HomePageAdmin(
        onProfileClick = {
            navController.navigate(PerfilAdminDestination().route) },
        onVerProgresosClick = {
            navController.navigate(VerProgresosDestination().route)
        },
        onNotificacionesClick = {
            navController.navigate(NotificacionesDestination().route)
        }
    )
}
