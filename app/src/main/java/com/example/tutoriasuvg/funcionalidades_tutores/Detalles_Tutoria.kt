package com.example.tutoriasuvg.funcionalidades_tutores

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
fun DetalleTutoria(
    onBackClick: () -> Unit,
    title: String,
    date: String,
    location: String,
    time: String,
    studentName: String,
    isVirtual: Boolean,
    link: String? = null
){
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
        // Contenido principal de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            // Row para ícono y la información
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Ícono de tutoría dentro de un círculo verde
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

                // Información de la tutoría
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = date, fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Mostrar ubicación (si es virtual, también muestra el link)
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
                    Text(text = studentName, fontSize = 16.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(64.dp)) 

            // Botón de "Tutoría Completada"
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
fun DetalleTutoriaPreview(){
    TutoriasUVGTheme {
        DetalleTutoria(
            onBackClick = { /*TODO*/ },
            title = "Física 3",
            date = "19/09/2024",
            location = "Virtual: Enlace Zoom",
            time = "15;00 hrs - 16:00 hrs",
            studentName = "Nombre del estudiante",
            isVirtual = true,
            link = "Enlace Zoom"
        )
    }
}
