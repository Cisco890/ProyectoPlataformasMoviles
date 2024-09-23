package com.example.tutoriasuvg.funcionalidades_estudiantes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun HomePageEstudiantesConTutorias() {
    Scaffold(
        topBar = { AppBarEstudiantesConTutorias() },
        bottomBar = { BottomNavigationBarConTutorias() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TarjetaTutoriaEstudiante(
                title = "Ecuaciones diferenciales I",
                date = "17/09/2024",
                location = "Presencial: CIT-503",
                time = "15:00 hrs - 16:00 hrs",
                link = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            TarjetaTutoriaEstudiante(
                title = "Física 3",
                date = "19/09/2024",
                location = "Virtual",
                time = "15:00 hrs - 16:00 hrs",
                link = "Enlace Zoom"
            )
        }
    }
}

@Composable
fun TarjetaTutoriaEstudiante(
    title: String,
    date: String,
    location: String,
    time: String,
    link: String?
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF007F39)) // Verde UVG
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.today),
                    contentDescription = "Icono de tutoría",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = date, fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))

                if (link != null) {
                    Text(
                        text = "$location: $link",
                        fontSize = 14.sp,
                        color = Color(0xFF007F39),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(text = location, fontSize = 14.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = time, fontSize = 14.sp, color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarEstudiantesConTutorias() {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_uvg_letras),
                    contentDescription = "Logo UVG",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(150.dp)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF007F39) // Verde UVG
        )
    )
}

@Composable
fun BottomNavigationBarConTutorias() { // Renombrada a BottomNavigationBarConTutorias
    // Barra de navegación inferior solo para visualización
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF007F39)), // Verde UVG
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(
            onClick = { /* No acción, solo visual */ },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.today),
                    contentDescription = "Tutorías planificadas",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text("Tutorías planificadas", color = Color.White, fontSize = 12.sp)
            }
        }
        TextButton(
            onClick = { /* No acción, solo visual */ },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.post_add_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                    contentDescription = "Solicitar tutorías",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text("Solicitud de tutorías", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageEstudiantesConTutoriasPreview() {
    TutoriasUVGTheme {
        HomePageEstudiantesConTutorias()
    }
}
