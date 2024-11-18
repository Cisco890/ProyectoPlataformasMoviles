package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
import com.example.tutoriasuvg.navigation.NavGraph
import com.example.tutoriasuvg.presentation.forgotpassword.ForgotPasswordDestination
import com.example.tutoriasuvg.presentation.login.LoadingScreen
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.login.LoginViewModelFactory
import com.example.tutoriasuvg.presentation.signup.RegisterDestination
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(this) }
                val loginRepository = FirebaseLoginRepository()
                val registerRepository = FirebaseRegisterRepository()
                val loginViewModelFactory = LoginViewModelFactory(application, loginRepository, sessionManager)

                var isLoading by remember { mutableStateOf(true) }
                var startDestination by remember { mutableStateOf(LoginDestination.route) }

                LaunchedEffect(Unit) {
                    isLoading = true

                    if (loginRepository.isUserLoggedIn()) {
                        val userId = loginRepository.getCurrentUserId()
                        userId?.let {
                            try {
                                val userTypeResult = loginRepository.getUserType(userId)
                                userTypeResult.fold(
                                    onSuccess = { userType ->
                                        delay(500)
                                        startDestination = when (userType) {
                                            "student" -> "homePageEstudiantes"
                                            "tutor" -> "homePageTutores"
                                            "admin" -> "homePageAdmin"
                                            else -> LoginDestination.route
                                        }
                                    },
                                    onFailure = { startDestination = LoginDestination.route }
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
                            val destination = when (userType) {
                                "student" -> "homePageEstudiantes"
                                "tutor" -> "homePageTutores"
                                "admin" -> "homePageAdmin"
                                else -> LoginDestination.route
                            }

                            navController.navigate(destination) {
                                popUpTo(LoginDestination.route) { inclusive = true }
                            }
                        },
                        loginViewModelFactory = loginViewModelFactory,
                        loginRepository = loginRepository,
                        registerRepository = registerRepository
                    )
                }
            }
        }
    }
}
