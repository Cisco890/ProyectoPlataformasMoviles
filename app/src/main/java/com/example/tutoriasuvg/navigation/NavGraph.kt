package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tutoriasuvg.presentation.forgotpassword.ForgotPasswordDestination
import com.example.tutoriasuvg.presentation.forgotpassword.forgotPasswordNavigation
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.login.loginNavigation
import com.example.tutoriasuvg.presentation.signup.RegisterDestination
import com.example.tutoriasuvg.presentation.signup.registerNavigation
import com.example.tutoriasuvg.presentation.signup.RegisterTutorDestination
import com.example.tutoriasuvg.presentation.signup.registerTutorNavigation


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginNavigation(
            onNavigateToRegister = { navController.navigate(RegisterDestination.route) },
            onNavigateToForgotPassword = { navController.navigate(ForgotPasswordDestination.route) },
            onLoginAsUser = { navController.navigate("user_screen") },
            onLoginAsTutor = { navController.navigate("tutor_screen") },
            onLoginAsAdmin = { navController.navigate("admin_screen") }
        )

        registerNavigation(
            onBackToLogin = { navController.popBackStack() }
        )

        registerTutorNavigation(
            onBackToLogin = { navController.popBackStack() }
        )

        forgotPasswordNavigation(
            onBackToLogin = { navController.popBackStack() }
        )

        composable("user_screen") { /* Contenido de la pantalla de usuario */ }
        composable("tutor_screen") { /* Contenido de la pantalla de tutor */ }
        composable("admin_screen") { /* Contenido de la pantalla de administrador */ }
    }
}


