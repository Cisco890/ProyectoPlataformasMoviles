package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.example.tutoriasuvg.navigation.NavGraph
import com.example.tutoriasuvg.presentation.forgotpassword.ForgotPasswordDestination
import com.example.tutoriasuvg.presentation.funcionalidades_admin.HomePageAdminDestination
import com.example.tutoriasuvg.presentation.login.LoadingScreen
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.login.LoginViewModelFactory
import com.example.tutoriasuvg.presentation.signup.RegisterDestination
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(this) }
                val loginRepository = FirebaseLoginRepository()

                // Crear LoginViewModelFactory con los parámetros requeridos
                val loginViewModelFactory = LoginViewModelFactory(application, loginRepository)

                var isLoading by remember { mutableStateOf(true) }
                var startDestination by remember { mutableStateOf(LoginDestination.route) }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    if (loginRepository.isUserLoggedIn()) {
                        val userId = loginRepository.getCurrentUserId()
                        if (userId != null) {
                            try {
                                val userTypeResult = loginRepository.getUserType(userId)
                                userTypeResult.fold(
                                    onSuccess = { userType ->
                                        startDestination = when (userType) {
                                            "student" -> "homePageEstudiantes"
                                            "tutor" -> "homePageTutores"
                                            "admin" -> HomePageAdminDestination().route
                                            else -> LoginDestination.route
                                        }
                                    },
                                    onFailure = {
                                        startDestination = LoginDestination.route
                                    }
                                )
                            } catch (e: Exception) {
                                startDestination = LoginDestination.route
                            }
                        }
                    } else {
                        startDestination = LoginDestination.route
                    }
                    isLoading = false
                }

                if (isLoading) {
                    LoadingScreen()
                } else {
                    NavGraph(
                        navController = navController,
                        sessionManager = sessionManager,
                        startDestination = startDestination,
                        onNavigateToForgotPassword = { navController.navigate(ForgotPasswordDestination.route) },
                        onNavigateToRegister = { navController.navigate(RegisterDestination.route) },
                        onLoginSuccess = { userType ->
                            coroutineScope.launch {
                                val userId = loginRepository.getCurrentUserId() ?: return@launch

                                sessionManager.saveUserType(userType, userId)

                                val destination = when (userType) {
                                    "student" -> "homePageEstudiantes"
                                    "tutor" -> "homePageTutores"
                                    "admin" -> HomePageAdminDestination().route
                                    else -> LoginDestination.route
                                }

                                navController.navigate(destination) {
                                    popUpTo(LoginDestination.route) { inclusive = true }
                                }
                            }
                        },
                        loginViewModelFactory = loginViewModelFactory  // Pasar loginViewModelFactory aquí
                    )
                }
            }
        }
    }
}
