package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
    val scope = rememberCoroutineScope()

    // Obtener el email del usuario autenticado
    val email = FirebaseAuth.getInstance().currentUser?.email

    // Estado de Carnet o Email
    var identifier by remember { mutableStateOf<String?>(null) }
    var isUsingCarnet by remember { mutableStateOf(true) } // Cambia a `false` si prefieres email

    // Obtener el carnet o email del usuario si aún no se ha obtenido
    LaunchedEffect(email) {
        if (email != null && identifier == null) {
            val firestore = FirebaseFirestore.getInstance()
            try {
                // Consulta Firestore para obtener el carnet en base al email
                val querySnapshot = firestore.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    identifier = document.getString("carnet") ?: email // Usa carnet si existe, si no usa email
                    isUsingCarnet = document.contains("carnet") // Si el documento tiene carnet, usa carnet

                    // Guardar en SessionManager
                    sessionManager.saveUserSession(
                        userType = document.getString("userType") ?: "unknown",
                        email = email,
                        identifier = identifier!!,
                        isUsingCarnet = isUsingCarnet
                    )
                } else {
                    identifier = email // En caso de no tener carnet, usamos el email
                    isUsingCarnet = false
                    sessionManager.saveUserSession(
                        userType = "unknown",
                        email = email,
                        identifier = email,
                        isUsingCarnet = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                identifier = email // En caso de error, usa email
                isUsingCarnet = false
            }
        }
    }

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
            onNavigateToRegisterTutor = { navController.navigate("register_tutor_screen") },
            registerRepository = registerRepository
        )

        // Navegación de Registro de Tutoría
        registerTutorNavigation(
            navController = navController,
            registerRepository = registerRepository
        )

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

        // Pantalla de progreso de horas usando el identificador (carnet o email)
        composable("progresoHorasBeca") {
            identifier?.let {
                ProgresoHorasBecaNavigation(
                    navController = navController,
                    identifier = it,
                    isUsingCarnet = isUsingCarnet,
                    sessionManager = sessionManager,
                    loginRepository = loginRepository
                )
            } ?: run {
                navController.navigate(LoginDestination.route) {
                    popUpTo(0)
                    launchSingleTop = true
                }
            }
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
