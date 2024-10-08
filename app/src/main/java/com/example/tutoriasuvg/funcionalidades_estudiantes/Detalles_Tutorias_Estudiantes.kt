package com.example.tutoriasuvg.funcionalidades_estudiantes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleTutoriaEstudiantes(
    onBackClick: () -> Unit,
    title: String,
    date: String,
    location: String,
    time: String,
    tutorName: String,
    isVirtual: Boolean,
    link: String? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalles", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
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
            Spacer(modifier = Modifier.height(32.dp))

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
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = date, fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            if (isVirtual && link != null) {
                Text(
                    text = "Virtual: $link",
                    fontSize = 16.sp,
                    color = Color(0xFF007F39),
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(text = location, fontSize = 16.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = time, fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = tutorName, fontSize = 16.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Acción de completar tutoría */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007F39)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(48.dp)
                    .width(200.dp)
            ) {
                Text(text = "Tutoría Completada", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleTutoriaEstudiantesPreview() {
    TutoriasUVGTheme {
        DetalleTutoriaEstudiantes(
            onBackClick = { /* No acción */ },
            title = "Física 3",
            date = "19/09/2024",
            location = "Virtual",
            time = "15:00 hrs - 16:00 hrs",
            tutorName = "Nombre del Tutor",
            isVirtual = true,
            link = "Link de Zoom"
        )
    }
}
