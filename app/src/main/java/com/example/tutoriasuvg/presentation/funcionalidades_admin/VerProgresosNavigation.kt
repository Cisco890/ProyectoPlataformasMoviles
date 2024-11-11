package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data class VerProgresosDestination(val route: String = "verProgresos")

@Composable
fun VerProgresosNavigation(navController: NavController) {
    VerProgresosScreen(
        onBackClick = { navController.popBackStack() }
    )
}
