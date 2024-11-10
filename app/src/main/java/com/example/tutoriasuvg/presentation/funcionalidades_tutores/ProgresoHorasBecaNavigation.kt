package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import kotlinx.serialization.Serializable
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.presentation.login.LoginDestination

import kotlinx.serialization.json.Json

@Serializable
data class ProgresoHorasBecaArgs(
    val identifier: String,
    val isUsingCarnet: Boolean
)

@Composable
fun ProgresoHorasBecaNavigation(
    navController: NavController,
    argsJson: String,
    viewModelFactory: ProgresoHorasBecaViewModelFactory
) {
    val args: ProgresoHorasBecaArgs = remember {
        Json.decodeFromString(argsJson)
    }

    val viewModel: ProgresoHorasBecaViewModel = viewModel(factory = viewModelFactory)

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggingOut) {
        if (uiState.isLoggingOut) {
            navController.navigate(LoginDestination.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    ProgresoHorasBecaRoute(
        uiState = uiState,
        onBackClick = { navController.popBackStack() },
        onLogoutClick = { viewModel.logout() }
    )
}
