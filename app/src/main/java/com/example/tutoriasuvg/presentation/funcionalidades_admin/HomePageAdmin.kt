package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
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
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.AppBar
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

data class Solicitud(
    val nombreEstudiante: String,
    val tutoria: String,
    val jornada: String,
    val diasPreferencia: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageAdmin(solicitudes: List<Solicitud>) {
    Scaffold(
        topBar = { AdminAppBar() },
        bottomBar = { AdminBottomNavigationBar() }
    ) { paddingValues ->
        if (solicitudes.isEmpty()) {
            // Mostrar mensaje cuando no hay solicitudes
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tienes solicitudes de tutorías por asignar",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        } else {
            // Mostrar la lista de solicitudes
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                solicitudes.forEach { solicitud ->
                    CardSolicitud(
                        nombreEstudiante = solicitud.nombreEstudiante,
                        tutoria = solicitud.tutoria,
                        jornada = solicitud.jornada,
                        diasPreferencia = solicitud.diasPreferencia
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
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

@Composable
fun AdminBottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF007F39)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.assignment),
                    contentDescription = "Asignar tutorías"
                )
            },
            label = { Text("Asignar tutorías") },
            selected = true,
            onClick = { /* Acción para asignar tutorías */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.schedule),
                    contentDescription = "Ver progresos"
                )
            },
            label = { Text("Ver progresos") },
            selected = false,
            onClick = { /* Acción para ver progresos */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notificaciones"
                )
            },
            label = { Text("Notificaciones") },
            selected = false,
            onClick = { /* Acción para notificaciones */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAppBar() {
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
        actions = {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "User Icon",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp),
                tint = Color.White
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF007F39)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomePageAdminWithSolicitudesPreview() {
    TutoriasUVGTheme {
        HomePageAdmin(
            solicitudes = listOf(
                Solicitud(
                    nombreEstudiante = "FERNANDO JOSÉ RUEDA RODAS",
                    tutoria = "Ecuaciones Diferenciales I",
                    jornada = "Vespertina",
                    diasPreferencia = "Jueves"
                ),
                Solicitud(
                    nombreEstudiante = "FERNANDO ANDREE HERNÁNDEZ",
                    tutoria = "Matemática Discreta I",
                    jornada = "Matutina",
                    diasPreferencia = "Martes, Viernes"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageAdminSinSolicitudesPreview() {
    TutoriasUVGTheme {
        HomePageAdmin(solicitudes = emptyList())
    }
}
