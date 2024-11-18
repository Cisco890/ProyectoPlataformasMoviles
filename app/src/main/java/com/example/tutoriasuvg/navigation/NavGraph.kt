package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.*
import com.example.tutoriasuvg.presentation.forgotpassword.forgotPasswordNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_admin.*
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.*
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.*
import com.example.tutoriasuvg.presentation.login.LoginDestination
import com.example.tutoriasuvg.presentation.login.LoginViewModelFactory
import com.example.tutoriasuvg.presentation.login.loginNavigation
import com.example.tutoriasuvg.presentation.signup.registerNavigation
import com.example.tutoriasuvg.presentation.signup.registerTutorNavigation
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
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
    onLoginSuccess: (String) -> Unit,
    loginViewModelFactory: LoginViewModelFactory,
    loginRepository: FirebaseLoginRepository,
    registerRepository: FirebaseRegisterRepository
) {
    val solicitudRepository = SolicitudRepository()
    val tutorRepository = TutorRepository()
    val tutoriasRepository = TutoriasRepository()

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

        // Pantallas adicionales de registro y recuperación de contraseña
        registerNavigation(
            onBackToLogin = {
                navController.navigate(LoginDestination.route) {
                    popUpTo(0)
                    launchSingleTop = true
                }
            },
            onNavigateToRegisterTutor = { navController.navigate("register_tutor_screen") },
            registerRepository = registerRepository
        )

        registerTutorNavigation(
            navController = navController,
            registerRepository = registerRepository
        )

        forgotPasswordNavigation(onBackToLogin = { navController.popBackStack() })

        // Pantallas de estudiantes
        composable("homePageEstudiantes") {
            val sessionManager = SessionManager(navController.context)
            val solicitudRepository = SolicitudRepository()

            HomePageEstudiantesNavigation(
                navController = navController,
                sessionManager = sessionManager,
                solicitudRepository = solicitudRepository
            )
        }


        composable("perfil_estudiante") {
            PerfilEstudianteNavigation(
                navController = navController,
                loginRepository = loginRepository,
                registerRepository = registerRepository
            )
        }

        composable("solicitud_tutoria") {
            SolicitudTutoriaNavigation(
                navController = navController,
                solicitudRepository = solicitudRepository
            )
        }

        composable("perfil_tutor") {
            var identifier by remember { mutableStateOf<String?>(null) }
            var isUsingCarnet by remember { mutableStateOf<Boolean?>(null) }

            LaunchedEffect(Unit) {
                identifier = sessionManager.getUserIdentifierSync()
                isUsingCarnet = sessionManager.isUsingCarnetSync()
            }

            if (identifier != null && isUsingCarnet != null) {
                val args = PerfilTutorArgs(userId = identifier!!, isUsingCarnet = isUsingCarnet!!)
                val argsJson = Json.encodeToString(args)

                val viewModelFactory = PerfilTutorViewModelFactory(
                    sessionManager = sessionManager,
                    loginRepository = loginRepository
                )

                PerfilTutorNavigation(
                    navController = navController,
                    argsJson = argsJson,
                    viewModelFactory = viewModelFactory
                )
            }
        }

        // Pantallas de tutor
        composable("homePageTutores") {
            var tutorId by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                tutorId = sessionManager.getUserIdentifierSync()
            }

            if (tutorId != null) {
                HomePageTutoresNavigation(
                    navController = navController,
                    solicitudRepository = solicitudRepository,
                    tutorId = tutorId!!
                )
            }
        }

        // Nueva ruta para "detalles_tutoria"
        composable(
            route = "detalles_tutoria/{tutoriaJson}",
            arguments = listOf(navArgument("tutoriaJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val tutoriaJson = backStackEntry.arguments?.getString("tutoriaJson")
            var userId by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                userId = sessionManager.getUserIdentifierSync()
            }

            if (tutoriaJson != null && userId != null) {
                val decodedJson = URLDecoder.decode(tutoriaJson, StandardCharsets.UTF_8.toString())
                val tutoria = Json.decodeFromString<Tutoria>(decodedJson)

                DetalleTutoria(
                    onBackClick = { navController.popBackStack() },
                    title = tutoria.title,
                    date = tutoria.date ?: "Fecha a definir",
                    location = tutoria.location ?: "Ubicación a definir",
                    time = tutoria.time ?: "Hora a definir",
                    studentName = "Nombre del estudiante",
                    isVirtual = tutoria.link != null,
                    link = tutoria.link,
                    userId = userId!!,
                    solicitudId = tutoria.id
                )
            }
        }

        composable(
            route = "detallesTutoriaEstudiantes/{tutoriaJson}",
            arguments = listOf(navArgument("tutoriaJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("tutoriaJson") ?: return@composable
            val decodedJson = URLDecoder.decode(encodedJson, StandardCharsets.UTF_8.toString())
            val tutoria = Json.decodeFromString<TutoriasEs>(decodedJson)

            DetallesTutoriaEstudiantesScreen(
                onBackClick = { navController.popBackStack() },
                tutoria = tutoria,
                isVirtual = tutoria.link != null
            )
        }


        // Pantallas de administrador
        composable(HomePageAdminDestination().route) {
            HomePageAdminNavigation(
                navController = navController,
                tutoriasRepository = tutoriasRepository
            )
        }

        composable(route = PerfilAdminDestination().route) {
            PerfilAdminNavigation(
                navController = navController,
                loginRepository = loginRepository,
                sessionManager = sessionManager
            )
        }

        composable(route = VerProgresosDestination().route) {
            VerProgresosNavigation(navController = navController)
        }

        composable(route = NotificacionesDestination().route) {
            NotificacionesNavigation(
                navController = navController,
                solicitudRepository = solicitudRepository,
                tutorRepository = tutorRepository
            )
        }
    }
}
