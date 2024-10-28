package com.example.tutoriasuvg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tutoriasuvg.presentation.funcionalidades_admin.HomePageAdminDestination
import com.example.tutoriasuvg.presentation.funcionalidades_admin.HomePageAdminNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_admin.NotificacionesDestination
import com.example.tutoriasuvg.presentation.funcionalidades_admin.NotificacionesNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_admin.VerProgresosDestination
import com.example.tutoriasuvg.presentation.funcionalidades_admin.VerProgresosNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.DetallesTutoriaNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.HomePageTutoresNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.ProgresoHorasBecaNavigation
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.Tutoria
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun UserNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    val userNavController = rememberNavController()

    NavHost(
        navController = userNavController,
        startDestination = startDestination
    ) {
        composable("homePageTutores") {
            HomePageTutoresNavigation(navController)
        }

        composable(
            route = "detalles_tutoria/{tutoriaJson}",
            arguments = listOf(navArgument("tutoriaJson") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val tutoriaJson = backStackEntry.arguments?.getString("tutoriaJson")
            val tutoria = tutoriaJson?.let { Json.decodeFromString<Tutoria>(it) }
            if (tutoria != null) {
                DetallesTutoriaNavigation(navController, tutoriaJson)
            }
        }

        composable("progresoHorasBeca") {
            ProgresoHorasBecaNavigation(navController)
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



