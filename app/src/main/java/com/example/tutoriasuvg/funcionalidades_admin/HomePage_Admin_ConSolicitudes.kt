package com.example.tutoriasuvg.funcionalidades_admin

import androidx.compose.foundation.BorderStroke
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
import com.example.tutoriasuvg.funcionalidades_tutores.AppBar
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageAdminConSolicitudesScreen() {
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { AdminBottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Tarjetas de solicitudes
            CardSolicitud(
                nombreEstudiante = "FERNANDO JOSÉ RUEDA RODAS",
                tutoria = "Ecuaciones Diferenciales I",
                jornada = "Vespertina",
                diasPreferencia = "Jueves"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CardSolicitud(
                nombreEstudiante = "FERNANDO ANDREE HERNÁNDEZ",
                tutoria = "Matemática Discreta I",
                jornada = "Matutina",
                diasPreferencia = "Martes, Viernes"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CardSolicitud(
                nombreEstudiante = "JUAN FRANCISCO MARTÍNEZ LEAL",
                tutoria = "Física 3",
                jornada = "Vespertina",
                diasPreferencia = "Jueves, Viernes"
            )
        }
    }
}

@Composable
fun CardSolicitud(
    nombreEstudiante: String,
    tutoria: String,
    jornada: String,
    diasPreferencia: String
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
                    .background(Color(0xFF007F39))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.today),
                    contentDescription = "Icono de solicitud",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información de la solicitud
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = nombreEstudiante, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Tutoría solicitada: $tutoria", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Jornada solicitada: $jornada", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Días de preferencia: $diasPreferencia", fontSize = 14.sp, color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageAdminConSolicitudesPreview() {
    TutoriasUVGTheme {
        HomePageAdminConSolicitudesScreen()
    }
}