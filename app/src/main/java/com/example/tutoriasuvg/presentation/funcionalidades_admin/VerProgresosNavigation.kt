package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun VerProgresosNavigation(navController: NavController) {
    VerProgresosScreen(
        onBackClick = { navController.popBackStack() }
    )
}
