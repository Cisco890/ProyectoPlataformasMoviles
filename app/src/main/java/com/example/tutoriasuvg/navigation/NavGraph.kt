package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutoriasuvg.presentation.forgotpassword.forgotPasswordNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_admin.*
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.*
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.login.loginNavigation
import com.example.tutoriasuvg.presentation.signup.registerNavigation
import com.example.tutoriasuvg.presentation.signup.registerTutorNavigation
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = LoginDestination.route,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginAsUser: () -> Unit,
    onLoginAsTutor: () -> Unit,
    onLoginAsAdmin: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login and Registration Destinations
        loginNavigation(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToForgotPassword = onNavigateToForgotPassword,
            onLoginAsUser = onLoginAsUser,
            onLoginAsTutor = onLoginAsTutor,
            onLoginAsAdmin = onLoginAsAdmin
        )

        registerNavigation(onBackToLogin = { navController.popBackStack() })
        registerTutorNavigation(onBackToLogin = { navController.popBackStack() })
        forgotPasswordNavigation(onBackToLogin = { navController.popBackStack() })

        // User-Type Specific Navigation
        composable("userNavGraph/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "user_home"
            when (userType) {
                "user_home", "tutor_home" -> {
                    navController.navigate("homePageTutores") {
                        popUpTo("userNavGraph/$userType") { inclusive = true }
                    }
                }
                "admin_home" -> {
                    navController.navigate(HomePageAdminDestination().route) {
                        popUpTo("userNavGraph/$userType") { inclusive = true }
                    }
                }
                else -> navController.navigate(LoginDestination.route) {
                    popUpTo("userNavGraph/$userType") { inclusive = true }
                }
            }
        }

        // Tutores Functionalities
        composable("homePageTutores") {
            HomePageTutoresNavigation(navController)
        }

        composable(
            route = "detalles_tutoria/{tutoriaJson}",
            arguments = listOf(navArgument("tutoriaJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val tutoriaJson = backStackEntry.arguments?.getString("tutoriaJson")
            if (tutoriaJson != null) {
                DetallesTutoriaNavigation(navController, tutoriaJson)
            }
        }

        composable("progresoHorasBeca") {
            ProgresoHorasBecaNavigation(navController)
        }

        // Admin Functionalities
        composable(HomePageAdminDestination().route) {
            HomePageAdminNavigation(navController)
        }

        composable(VerProgresosDestination().route) {
            VerProgresosNavigation(navController)
        }

        composable(NotificacionesDestination().route) {
            NotificacionesNavigation(navController)
        }
    }
}
