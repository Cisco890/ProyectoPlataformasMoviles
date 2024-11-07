package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.navigation.NavGraph
import com.example.tutoriasuvg.presentation.forgotpassword.ForgotPasswordDestination
import com.example.tutoriasuvg.presentation.funcionalidades_admin.HomePageAdminDestination
import com.example.tutoriasuvg.presentation.login.LoadingScreen
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.signup.RegisterDestination
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(this) }
                val firebaseAuth = FirebaseAuth.getInstance()
                val firestore = FirebaseFirestore.getInstance()

                var isLoading by remember { mutableStateOf(true) }
                var startDestination by remember { mutableStateOf(LoginDestination.route) }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    val currentUser = firebaseAuth.currentUser
                    if (currentUser != null) {
                        val userId = currentUser.uid
                        val userDocRef = firestore.collection("user_sessions").document(userId)

                        try {
                            val document = userDocRef.get().await()
                            if (document.exists()) {
                                val userType = document.getString("userType") ?: "student"
                                startDestination = when (userType) {
                                    "student" -> "homePageEstudiantes"
                                    "tutor" -> "homePageTutores"
                                    "admin" -> HomePageAdminDestination().route
                                    else -> LoginDestination.route
                                }
                            } else {
                                startDestination = LoginDestination.route
                            }
                        } catch (e: Exception) {
                            startDestination = LoginDestination.route
                        } finally {
                            isLoading = false
                        }
                    } else {
                        startDestination = LoginDestination.route
                        isLoading = false
                    }
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
                                val userId = firebaseAuth.currentUser?.uid ?: return@launch

                                firestore.collection("user_sessions").document(userId).set(
                                    mapOf(
                                        "userType" to userType,
                                        "isLoggedIn" to true
                                    )
                                )

                                sessionManager.saveUserType(userType, firebaseAuth.currentUser?.email.orEmpty())

                                delay(200)

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
                        }
                    )
                }
            }
        }
    }
}
