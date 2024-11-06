package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.serialization.Serializable


@Composable
fun HomePageEstudiantes(
    viewModel: HomePageEstudiantesViewModel = viewModel(),
    onTutoriaEstudianteClick: (TutoriasEs) -> Unit,  // Usar TutoriasEs como tipo de parámetro
    onPerfilClick: () -> Unit,
    onSolicitudTutoriaClick: () -> Unit
) {
    val estudianteTutoria = viewModel.tutorias.collectAsState().value
    var isNavigating by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppBar(onPerfilClick = onPerfilClick) },
        bottomBar = { BottomNavigationBar(onSolicitudTutoriaClick = onSolicitudTutoriaClick) }
    ) { paddingValues ->
        if (estudianteTutoria.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tienes tutorías planificadas",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                estudianteTutoria.forEach { tutoriasEs ->
                    CartasTutorias(
                        title = tutoriasEs.title,
                        date = tutoriasEs.date,
                        location = tutoriasEs.location,
                        time = tutoriasEs.time,
                        link = tutoriasEs.link,
                        onClick = {
                            if (!isNavigating) {
                                isNavigating = true
                                onTutoriaEstudianteClick(tutoriasEs)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun CartasTutorias(
    title: String,
    date: String,
    location: String,
    time: String,
    link: String?,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
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
                Image(
                    painter = painterResource(id = R.drawable.today),
                    contentDescription = "Icono de tutoría",
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
                        text = "$location$link",
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
fun AppBar(onPerfilClick: () -> Unit) {
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
            IconButton(onClick = onPerfilClick) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp),
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF007F39)
        )
    )
}

@Composable
fun BottomNavigationBar(onSolicitudTutoriaClick: () -> Unit) {
    NavigationBar(
        containerColor = Color(0xFF007F39)
    ) {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.post_add_24dp_e8eaed_fill0_wght400_grad0_opsz24), contentDescription = "Solicitud de tutorías") },
            label = { Text("Solicitud de tutorías") },
            selected = false,
            onClick = onSolicitudTutoriaClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageEstudiantesPreview() {
    val mockViewModel = HomePageEstudiantesViewModel(
        tutoriasIniciales = listOf(
            TutoriasEs(
                title = "Ecuaciones diferenciales I",
                date = "17/09/2024",
                location = "Presencial: CIT-503",
                time = "15:00 hrs - 16:00 hrs",
                link = null
            ),
            TutoriasEs(
                title = "Física 3",
                date = "19/09/2024",
                location = "Virtual: ",
                time = "15:00 hrs - 16:00 hrs",
                link = "Enlace Zoom"
            )
        )
    )

    TutoriasUVGTheme {
        HomePageEstudiantes(
            viewModel = mockViewModel,
            onTutoriaEstudianteClick = { /* Do something on tutoría click */ },
            onPerfilClick = { /* Do something on profile click */ },
            onSolicitudTutoriaClick = { /* Do something on solicitud de tutoría click */ }
        )
    }
}
