package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Serializable
data class PerfilTutorArgs(
    val userId: String,
    val isUsingCarnet: Boolean
)

@Composable
fun PerfilTutorNavigation(
    navController: NavController,
    argsJson: String,
    viewModelFactory: PerfilTutorViewModelFactory
) {
    val args = Json.decodeFromString<PerfilTutorArgs>(argsJson)
    val viewModel: PerfilTutorViewModel = viewModel(factory = viewModelFactory)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadProfileData()
    }

    val profileData by viewModel.profileData.collectAsState()

    profileData?.let { data ->
        PerfilTutorScreen(
            profileData = data,
            onLogoutClick = {
                coroutineScope.launch {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo("perfil_tutor") { inclusive = true }
                    }
                }
            },
            onBackClick = { navController.popBackStack() },
            onLoginNavigate = {
                navController.navigate("login_screen") {
                    popUpTo("perfil_tutor") { inclusive = true }
                }
            }
        )
    }
}

fun NavController.navigateToPerfilTutor(args: PerfilTutorArgs) {
    val argsJson = Json.encodeToString(args)
    this.navigate("perfil_tutor/$argsJson")
}
