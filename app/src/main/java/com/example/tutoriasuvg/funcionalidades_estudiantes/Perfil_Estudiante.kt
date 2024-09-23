package com.example.tutoriasuvg.funcionalidades_estudiantes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilEstudiante() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { /* Acción de ir hacia atrás */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
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
            Spacer(modifier = Modifier.height(50.dp))

            // Ícono de perfil en un fondo cuadrado verde
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFF007F39), shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Ícono de perfil",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Nombre del Estudiante", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Carnet del Estudiante", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Año que Cursa el Estudiante", fontSize = 16.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Acción de volverse tutor */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007F39)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(48.dp)
                    .width(200.dp)
            ) {
                Text(text = "Volverme Tutor", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilEstudiantePreview() {
    TutoriasUVGTheme {
        PerfilEstudiante()
    }
}
