package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutoriasuvg.data.model.TutoriaAsignada

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorAsignacionesScreen(
    viewModel: TutorNotificacionesViewModel = viewModel(),
    tutorId: String,
    onBackClick: () -> Unit
) {
    val asignaciones by viewModel.tutorAsignaciones.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asignaciones de Tutoría") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (asignaciones.isEmpty()) {
                Text(
                    text = "No tienes asignaciones de tutoría.",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                asignaciones.forEach { tutoria ->
                    AsignacionCard(tutoria)
                }
            }
        }
    }
}

@Composable
fun AsignacionCard(tutoria: TutoriaAsignada) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Curso: ${tutoria.courseName}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Fecha: ${tutoria.date}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Hora: ${tutoria.time}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Modalidad: ${tutoria.modalidad}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Ubicación: ${tutoria.location}", fontSize = 14.sp)
        }
    }
}
