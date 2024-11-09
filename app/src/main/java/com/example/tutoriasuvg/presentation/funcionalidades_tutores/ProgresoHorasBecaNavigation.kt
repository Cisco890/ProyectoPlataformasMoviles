package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.example.tutoriasuvg.presentation.login.LoadingScreen
import kotlinx.coroutines.delay

@Composable
fun ProgresoHorasBecaNavigation(
    navController: NavController,
    identifier: String,
    isUsingCarnet: Boolean,
    sessionManager: SessionManager,
    loginRepository: FirebaseLoginRepository
) {
    var isLoggingOut by remember { mutableStateOf(false) }
    var showProgresoHorasBeca by remember { mutableStateOf(false) }

    val viewModel: ProgresoHorasBecaViewModel = viewModel(
        key = "ProgresoHorasBecaViewModel_$identifier",
        factory = ProgresoHorasBecaViewModelFactory(identifier, isUsingCarnet)
    )

    val isLoading = viewModel.isLoading.collectAsState().value

    LaunchedEffect(identifier, isUsingCarnet) {
        viewModel.setIdentifier(identifier, isUsingCarnet)
    }

    LaunchedEffect(isLoading) {
        if (!isLoading) {
            delay(500)
            showProgresoHorasBeca = true
        }
    }

    if (isLoggingOut) {
        LaunchedEffect(Unit) {
            loginRepository.logout()
            sessionManager.clearSession()
            viewModel.clearData()

            navController.navigate("login_screen") {
                popUpTo(0) { inclusive = true }
            }
            isLoggingOut = false
        }
    }

    when {
        isLoading -> {
            LoadingScreen()
        }
        showProgresoHorasBeca -> {
            ProgresoHorasBeca(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onLogoutClick = {
                    isLoggingOut = true
                }
            )
        }
    }
}
