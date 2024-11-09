package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository

@Composable
fun ProgresoHorasBecaNavigation(
    navController: NavController,
    sessionManager: SessionManager,
    loginRepository: FirebaseLoginRepository
) {
    var isLoggingOut by remember { mutableStateOf(false) }

    if (isLoggingOut) {
        LaunchedEffect(Unit) {
            loginRepository.logout()
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
