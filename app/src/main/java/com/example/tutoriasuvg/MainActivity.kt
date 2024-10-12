package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.tutoriasuvg.presentation.forgotpassword.ForgotPasswordScreen
import com.example.tutoriasuvg.presentation.login.LoginScreen
import com.example.tutoriasuvg.presentation.signup.RegisterStudentTutorScreen
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login_screen"
    ) {
        composable(route = "login_screen") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register_screen")
                },
                onNavigateTForgotPassword = {
                    navController.navigate("forgot_password_screen")
                }
            )
        }

        composable(route = "register_screen") {
            RegisterStudentTutorScreen(
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Forgot Password Screen
        composable(route = "forgot_password_screen") {
            ForgotPasswordScreen(
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}

