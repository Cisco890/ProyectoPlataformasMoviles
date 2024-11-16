package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.data.repository.TutoriaRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesTutoriaEstudiantesScreen(
    onBackClick: () -> Unit,
    tutoria: TutoriasEs,
    isVirtual: Boolean,
    tutorId: String,
    tutoriaAsignadaId: Int,
    tutoriaRepository: TutoriaRepository
) {
    val viewModel: DetallesTutoriaEstudiantesViewModel = viewModel(
        factory = DetallesTutoriaEstudiantesViewModelFactory(tutoriaRepository)
    )

    val context = LocalContext.current
    val isCompleted by viewModel.isCompleted.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var showConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            showConfirmationDialog = false
            Toast.makeText(context, "Tutoría completada con éxito", Toast.LENGTH_SHORT).show()
            onBackClick()
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, "Error al completar tutoría: $it", Toast.LENGTH_LONG).show()
            viewModel.clearErrorMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalles", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF007F39)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF007F39))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.today),
                        contentDescription = "Icono de tutoría",
                        modifier = Modifier.size(40.dp),
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = tutoria.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = tutoria.date ?: "Fecha a definir", fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (isVirtual && tutoria.link != null) {
                        Text(
                            text = "Virtual: ${tutoria.link}",
                            fontSize = 16.sp,
                            color = Color(0xFF007F39),
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(text = tutoria.location ?: "Ubicación a definir", fontSize = 16.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = tutoria.time ?: "Hora a definir", fontSize = 16.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            if (!isCompleted) {
                Button(
                    onClick = { showConfirmationDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007F39)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(48.dp)
                        .width(200.dp)
                ) {
                    Text(text = "Tutoría Completada", color = Color.White, fontSize = 16.sp)
                }
            } else {
                Text(text = "Tutoría ya completada", color = Color.Gray)
            }
        }
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text(text = "Confirmar acción") },
            text = { Text(text = "¿Está seguro de que desea marcar esta tutoría como completada? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    if (tutoria.id.isNotBlank() && tutoriaAsignadaId >= 0 && tutorId.isNotBlank()) {
                        viewModel.completarTutoria(
                            solicitudId = tutoria.id,
                            tutoriaAsignadaId = tutoriaAsignadaId,
                            tutorId = tutorId
                        )
                    } else {
                        Toast.makeText(context, "Error: Datos de la tutoría incompletos", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Sí, completar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmationDialog = false }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
