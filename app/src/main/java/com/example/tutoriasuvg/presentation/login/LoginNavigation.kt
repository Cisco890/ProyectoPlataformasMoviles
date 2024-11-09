package com.example.tutoriasuvg.presentation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination {
    const val route = "login_screen"
}

fun NavGraphBuilder.loginNavigation(
    loginViewModelFactory: LoginViewModelFactory,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    composable(route = LoginDestination.route) {
        val loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = loginViewModelFactory)

        LoginScreen(
            loginViewModel = loginViewModel,
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToForgotPassword = onNavigateToForgotPassword,
            onLoginSuccess = onLoginSuccess
        )
    }
}
