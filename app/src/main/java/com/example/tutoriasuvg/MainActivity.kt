package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.navigation.NavGraph
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(this) }

                var startDestination by remember { mutableStateOf(LoginDestination.route) }

                LaunchedEffect(sessionManager) {
                    combine(
                        sessionManager.isLoggedIn,
                        sessionManager.userType
                    ) { isLoggedIn, userType ->
                        if (isLoggedIn && userType != null) {
                            startDestination = when (userType) {
                                "user" -> "userNavGraph/user_home"
                                "tutor" -> "userNavGraph/tutor_home"
                                "admin" -> "userNavGraph/admin_home"
                                else -> LoginDestination.route
                            }
                        } else {
                            startDestination = LoginDestination.route
                        }
                    }.collect()
                }


                NavGraph(
                    navController = navController,
                    startDestination = startDestination,
                    onNavigateToForgotPassword = { navController.navigate("forgot_password") },
                    onNavigateToRegister = { navController.navigate("register") },
                    onLoginAsUser = {
                        navController.navigate("userNavGraph/user_home") {
                            popUpTo(LoginDestination.route) { inclusive = true }
                        }
                    },
                    onLoginAsTutor = {
                        navController.navigate("userNavGraph/tutor_home") {
                            popUpTo(LoginDestination.route) { inclusive = true }
                        }
                    },
                    onLoginAsAdmin = {
                        navController.navigate("userNavGraph/admin_home") {
                            popUpTo(LoginDestination.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
