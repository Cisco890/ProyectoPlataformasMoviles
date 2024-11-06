package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.presentation.forgotpassword.forgotPasswordNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_admin.*
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.*
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.*
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.login.loginNavigation
import com.example.tutoriasuvg.presentation.signup.RegisterDestination
import com.example.tutoriasuvg.presentation.signup.registerNavigation
import com.example.tutoriasuvg.presentation.signup.registerTutorNavigation
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = LoginDestination.route,
    sessionManager: SessionManager,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginAsUser: () -> Unit,
    onLoginAsTutor: () -> Unit,
    onLoginAsAdmin: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginNavigation(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToForgotPassword = onNavigateToForgotPassword,
            onLoginAsUser = onLoginAsUser,
            onLoginAsTutor = onLoginAsTutor,
            onLoginAsAdmin = onLoginAsAdmin
        )

        registerNavigation(
            onBackToLogin = { navController.popBackStack() },
            onNavigateToRegisterTutor = { navController.navigate("register_tutor_screen") }
        )

        registerTutorNavigation(onBackToLogin = { navController.popBackStack() })
        forgotPasswordNavigation(onBackToLogin = { navController.popBackStack() })

        composable("userNavGraph/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "user_home"
            when (userType) {
                "user_home" -> {
                    navController.navigate("homePageEstudiantes") {
                        popUpTo("userNavGraph/$userType") { inclusive = true }
                    }
                }
                "tutor_home" -> {
                    navController.navigate("homePageTutores") {
                        popUpTo("userNavGraph/$userType") { inclusive = true }
                    }
                }
                "admin_home" -> {
                    navController.navigate(HomePageAdminDestination().route) {
                        popUpTo("userNavGraph/$userType") { inclusive = true }
                    }
                }
                else -> navController.navigate(LoginDestination.route) {
                    popUpTo("userNavGraph/$userType") { inclusive = true }
                }
            }
        }

        // Tutor Home Page
        composable("homePageTutores") {
            HomePageTutoresNavigation(navController)
        }

        composable(
            route = "detalles_tutoria/{tutoriaJson}",
            arguments = listOf(navArgument("tutoriaJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val tutoriaJson = backStackEntry.arguments?.getString("tutoriaJson")
            if (tutoriaJson != null) {
                DetallesTutoriaNavigation(navController, tutoriaJson)
            }
        }



        composable(
            route = DetallesTutoriaEstudiantesRoute,
            arguments = listOf(navArgument("tutoriaJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedTutoriasEsJson = backStackEntry.arguments?.getString("tutoriaJson")
            if (encodedTutoriasEsJson != null) {
                val decodedTutoriasEsJson = URLDecoder.decode(encodedTutoriasEsJson, StandardCharsets.UTF_8.toString())
                val tutoria = Json.decodeFromString<TutoriasEs>(decodedTutoriasEsJson)

                DetallesTutoriaEstudiantesScreen(
                    onBackClick = { navController.popBackStack() },
                    tutoria = tutoria,
                    studentName = "Nombre del estudiante",
                    isVirtual = tutoria.link != null
                )
            }
        }


        // Student Home Page
        composable("homePageEstudiantes") {
            HomePageEstudiantesNavigation(navController = navController)
        }

        // Perfil del Estudiante
        composable("perfil_estudiante") {
            PerfilEstudianteNavigation(navController)
        }

        // Solicitar Tutoría
        composable("solicitud_tutoria") {
            SolicitudTutoriaNavigation(navController)
        }

        composable("progresoHorasBeca") {
            ProgresoHorasBecaNavigation(navController, sessionManager)
        }

        composable(HomePageAdminDestination().route) {
            HomePageAdminNavigation(navController)
        }

        composable(VerProgresosDestination().route) {
            VerProgresosNavigation(navController)
        }

        composable(NotificacionesDestination().route) {
            NotificacionesNavigation(navController)
        }
    }
}
