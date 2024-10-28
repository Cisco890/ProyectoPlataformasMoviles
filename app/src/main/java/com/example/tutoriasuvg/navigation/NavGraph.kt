package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tutoriasuvg.presentation.forgotpassword.ForgotPasswordDestination
import com.example.tutoriasuvg.presentation.forgotpassword.forgotPasswordNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_admin.HomePageAdminDestination
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
            onLoginAsUser = { navController.navigate("userNavGraph/user_home") },
            onLoginAsTutor = { navController.navigate("userNavGraph/tutor_home") },
            onLoginAsAdmin = { navController.navigate("userNavGraph/admin_home") }
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

        composable("userNavGraph/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "user_home"
            val userNavStartDestination = when (userType) {
                "user_home" -> HomePageAdminDestination().route
                "tutor_home" -> "homePageTutores"
                "admin_home" -> HomePageAdminDestination().route
                else -> "login_screen"
            }
            UserNavGraph(navController = navController, startDestination = userNavStartDestination)
        }
    }
}




