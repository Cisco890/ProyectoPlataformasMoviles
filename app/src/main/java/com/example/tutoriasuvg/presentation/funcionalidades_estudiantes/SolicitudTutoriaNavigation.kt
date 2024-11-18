package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.SolicitudRepository

@Composable
fun SolicitudTutoriaNavigation(
    navController: NavController,
    solicitudRepository: SolicitudRepository
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var studentId = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        studentId.value = sessionManager.getUserIdentifierSync()
    }

    if (studentId.value != null) {
        val factory = SolicitudTutoriaViewModelFactory(
            solicitudRepository = solicitudRepository,
            studentId = studentId.value!!
        )
        val viewModel: SolicitudTutoriaViewModel = viewModel(factory = factory)

        SolicitudTutoriaScreen(
            viewModel = viewModel,
            navController = navController,
            onBackClick = { navController.popBackStack() }
        )
    }
}
