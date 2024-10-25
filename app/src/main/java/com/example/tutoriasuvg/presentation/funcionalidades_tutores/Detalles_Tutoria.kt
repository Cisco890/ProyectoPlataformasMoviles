package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import DetallesTutoriaViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleTutoria(
    onBackClick: () -> Unit,
    title: String,
    date: String,
    location: String,
    time: String,
    studentName: String,
    isVirtual: Boolean,
    link: String? = null,
    viewModel: DetallesTutoriaViewModel = viewModel()
){
    val isCompleted = viewModel.isCompleted.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalles", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { /* Acción para volver */ }) {
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
        // Contenido principal de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            // Información de la tutoría
            DetalleTutoria(
                title = title,
                date = date,
                location = location,
                time = time,
                studentName = studentName,
                isVirtual = isVirtual,
                link = link
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Botón de "Tutoría Completada"
            if (!isCompleted) {
                Button(
                    onClick = { viewModel.completarTutoria() },  // Marcamos la tutoría como completada
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
}
@Composable
fun DetalleTutoria(
    title: String,
    date: String,
    location: String,
    time: String,
    studentName: String,
    isVirtual: Boolean,
    link: String?
) {
    // Información detallada de la tutoría (ya existente)
    // Solo encapsulamos la estructura existente aquí
}

@Preview(showBackground = true)
@Composable
fun DetalleTutoriaPreview(){
    TutoriasUVGTheme {
        DetalleTutoria(
            onBackClick = { /*TODO*/ },
            title = "Física 3",
            date = "19/09/2024",
            location = "Virtual: Enlace Zoom",
            time = "15:00 hrs - 16:00 hrs",
            studentName = "Nombre del estudiante",
            isVirtual = true,
            link = "Enlace Zoom"
        )
    }
}
