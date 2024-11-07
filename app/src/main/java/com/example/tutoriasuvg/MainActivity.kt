package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.navigation.NavGraph
import com.example.tutoriasuvg.presentation.forgotpassword.ForgotPasswordDestination
import com.example.tutoriasuvg.presentation.login.LoadingScreen
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.signup.RegisterDestination
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(this) }

                var startDestination by remember { mutableStateOf<String?>(null) }
                var isLoading by remember { mutableStateOf(false) }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    val isLoggedIn = sessionManager.isLoggedInSync()
                    val userType = sessionManager.getUserTypeSync()

                    if (isLoggedIn) {
                        isLoading = true
                        startDestination = when (userType) {
                            "user" -> "userNavGraph/user_home"
                            "tutor" -> "userNavGraph/tutor_home"
                            "admin" -> "userNavGraph/admin_home"
                            else -> LoginDestination.route
                        }
                    } else {
                        startDestination = LoginDestination.route
                    }

                    if (isLoading) {
                        kotlinx.coroutines.delay(0)
                        isLoading = false
                    }
                }

                if (isLoading) {
                    LoadingScreen()
                } else if (startDestination != null) {
                    NavGraph(
                        navController = navController,
                        sessionManager = sessionManager,
                        startDestination = startDestination!!,
                        onNavigateToForgotPassword = { navController.navigate(ForgotPasswordDestination.route) },
                        onNavigateToRegister = { navController.navigate(RegisterDestination.route) },
                        onLoginAsUser = {
                            coroutineScope.launch {
                                isLoading = true
                                sessionManager.saveLoginSession("user_email", "user")
                                navController.navigate("userNavGraph/user_home") {
                                    popUpTo(LoginDestination.route) { inclusive = true }
                                }
                                isLoading = false
                            }
                        },
                        onLoginAsTutor = {
                            coroutineScope.launch {
                                isLoading = true
                                sessionManager.saveLoginSession("tutor_email", "tutor")
                                navController.navigate("userNavGraph/tutor_home") {
                                    popUpTo(LoginDestination.route) { inclusive = true }
                                }
                                isLoading = false
                            }
                        },
                        onLoginAsAdmin = {
                            coroutineScope.launch {
                                isLoading = true
                                sessionManager.saveLoginSession("admin_email", "admin")
                                navController.navigate("userNavGraph/admin_home") {
                                    popUpTo(LoginDestination.route) { inclusive = true }
                                }
                                isLoading = false
                            }
                        }
                    )
                }
            }
        }
    }
}
