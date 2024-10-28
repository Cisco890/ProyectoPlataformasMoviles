package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.navigation.NavGraph
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val sessionManager = remember { SessionManager(context) }

                var startDestination by remember { mutableStateOf("login_screen") }

                LaunchedEffect(Unit) {
                    sessionManager.isLoggedIn.collect { isLoggedIn ->
                        sessionManager.userType.collect { userType ->
                            startDestination = if (isLoggedIn && userType != null) {
                                when (userType) {
                                    "user" -> "user_screen"
                                    "tutor" -> "tutor_screen"
                                    "admin" -> "admin_screen"
                                    else -> "login_screen"
                                }
                            } else {
                                "login_screen"
                            }
                        }
                    }
                }

                NavGraph(navController = navController, startDestination = startDestination)
            }
        }
    }
}
