package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.local.SessionManager

@Composable
fun ProgresoHorasBecaNavigation(navController: NavController, sessionManager: SessionManager) {
    var isLoggingOut by remember { mutableStateOf(false) }

    if (isLoggingOut) {
        LaunchedEffect(Unit) {
            sessionManager.clearSession()
            navController.navigate("login_screen") {
                popUpTo(0) { inclusive = true }
            }
            isLoggingOut = false
        }
    }
    ProgresoHorasBeca(
        onBackClick = {
            navController.popBackStack()
        },
        onLogoutClick = {
           isLoggingOut = true
        }
    )
}
