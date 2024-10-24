package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.tutoriasuvg.presentation.forgotpassword.ForgotPasswordDestination
import com.example.tutoriasuvg.presentation.forgotpassword.forgotPasswordNavigation
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.login.loginNavigation
import com.example.tutoriasuvg.presentation.signup.RegisterDestination
import com.example.tutoriasuvg.presentation.signup.registerNavigation
import com.example.tutoriasuvg.presentation.signup.RegisterTutorDestination
import com.example.tutoriasuvg.presentation.signup.registerTutorNavigation

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.route 
    ) {
        loginNavigation(
            onNavigateToRegister = { navController.navigate(RegisterDestination.route) },
            onNavigateToForgotPassword = { navController.navigate(ForgotPasswordDestination.route) }
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
    }
}
