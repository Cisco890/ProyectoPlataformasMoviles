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
import com.example.tutoriasuvg.presentation.login.LoginViewModelFactory
import com.example.tutoriasuvg.presentation.login.loginNavigation
import com.example.tutoriasuvg.presentation.signup.registerNavigation
import com.example.tutoriasuvg.presentation.signup.registerTutorNavigation
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = LoginDestination.route,
    sessionManager: SessionManager,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (String) -> Unit,
    loginViewModelFactory: LoginViewModelFactory,
    loginRepository: FirebaseLoginRepository
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Navegación de Login
        loginNavigation(
            loginViewModelFactory = loginViewModelFactory,
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToForgotPassword = onNavigateToForgotPassword,
            onLoginSuccess = { userType ->
                when (userType) {
                    "student" -> navController.navigate("homePageEstudiantes") {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                    "tutor" -> navController.navigate("homePageTutores") {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                    "admin" -> navController.navigate(HomePageAdminDestination().route) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                    else -> navController.navigate(LoginDestination.route)
                }
            }
        )

        // Navegación de Registro y Recuperación de Contraseña
        registerNavigation(
            onBackToLogin = {
                navController.navigate(LoginDestination.route) {
                    popUpTo(0)
                    launchSingleTop = true
                }
            },
            onNavigateToRegisterTutor = { navController.navigate("register_tutor_screen") }
        )

        // Llamada corregida para `registerTutorNavigation`
        registerTutorNavigation(navController = navController)

        forgotPasswordNavigation(onBackToLogin = { navController.popBackStack() })

        // Pantallas de estudiantes
        composable("homePageEstudiantes") {
            HomePageEstudiantesNavigation(navController = navController)
        }

        composable("perfil_estudiante") {
            PerfilEstudianteNavigation(navController, loginRepository)
        }

        composable("solicitud_tutoria") {
            SolicitudTutoriaNavigation(navController)
        }

        composable("progresoHorasBeca") {
            ProgresoHorasBecaNavigation(navController, sessionManager, loginRepository)
        }

        // Pantallas de tutor
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

        // Pantallas de administrador
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
