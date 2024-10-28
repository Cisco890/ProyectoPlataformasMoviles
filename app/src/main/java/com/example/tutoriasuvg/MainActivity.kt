package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.navigation.NavGraph
import com.example.tutoriasuvg.presentation.login.LoadingScreen
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(this) }

                var startDestination by remember { mutableStateOf(LoginDestination.route) }
                var isLoading by remember { mutableStateOf(false) }

                LaunchedEffect(sessionManager) {
                    combine(
                        sessionManager.isLoggedIn,
                        sessionManager.userType
                    ) { isLoggedIn, userType ->
                        startDestination = if (isLoggedIn && userType != null) {
                            when (userType) {
                                "user" -> "userNavGraph/user_home"
                                "tutor" -> "userNavGraph/tutor_home"
                                "admin" -> "userNavGraph/admin_home"
                                else -> LoginDestination.route
                            }
                        } else {
                            LoginDestination.route
                        }
                    }.collect()
                }

                if (isLoading) {
                    LoadingScreen()
                } else {
                    NavGraph(
                        navController = navController,
                        startDestination = startDestination,
                        sessionManager = sessionManager,
                        onNavigateToForgotPassword = { navController.navigate("forgot_password") },
                        onNavigateToRegister = { navController.navigate("register") },
                        onLoginAsUser = { startDelayedNavigation(navController, "userNavGraph/user_home", setLoading = { isLoading = it }) },
                        onLoginAsTutor = { startDelayedNavigation(navController, "userNavGraph/tutor_home", setLoading = { isLoading = it }) },
                        onLoginAsAdmin = { startDelayedNavigation(navController, "userNavGraph/admin_home", setLoading = { isLoading = it }) }
                    )
                }
            }
        }
    }

    private fun startDelayedNavigation(
        navController: NavHostController,
        route: String,
        setLoading: (Boolean) -> Unit
    ) {
        setLoading(true)

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            setLoading(false)
            navController.navigate(route) {
                popUpTo(LoginDestination.route) { inclusive = true }
            }
        }
    }
}
