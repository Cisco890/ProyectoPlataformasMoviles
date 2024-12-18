package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class PerfilAdminArgs(
    val adminId: String
)

@Serializable
data class PerfilAdminDestination(val route: String = "perfil_admin")



@Composable
fun PerfilAdminNavigation(
    navController: NavController,
    loginRepository: FirebaseLoginRepository,
    sessionManager: SessionManager
) {
    val viewModel: PerfilAdminViewModel = viewModel(
        factory = PerfilAdminViewModelFactory(loginRepository, sessionManager)
    )

    val adminName by viewModel.adminName.collectAsState()
    val logoutEvent by viewModel.logoutEvent.collectAsState()

    LaunchedEffect(logoutEvent) {
        if (logoutEvent) {
            navController.navigate("login_screen") {
                popUpTo("perfil_admin") { inclusive = true }
            }
        }
    }

    adminName?.let { name ->
        PerfilAdminScreen(
            adminName = name,
            onLogoutClick = {
                viewModel.logout()
            },
            onBackClick = { navController.popBackStack() }
        )
    }
}


fun NavController.navigateToPerfilAdmin(args: PerfilAdminArgs) {
    val argsJson = Json.encodeToString(args)
    this.navigate("perfil_admin/$argsJson")
}
