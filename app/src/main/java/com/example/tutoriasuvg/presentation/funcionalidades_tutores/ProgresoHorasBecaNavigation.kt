package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ProgresoHorasBecaNavigation(navController: NavController) {
    ProgresoHorasBeca(
        onBackClick = {
            navController.popBackStack()
        }
    )
}
