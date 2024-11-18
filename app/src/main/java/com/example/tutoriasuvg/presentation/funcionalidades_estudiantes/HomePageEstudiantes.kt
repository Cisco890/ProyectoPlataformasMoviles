package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@Composable
fun HomePageEstudiantes(
    viewModel: HomePageEstudiantesViewModel,
    onTutoriaEstudianteClick: (TutoriasEs) -> Unit,
    onPerfilClick: () -> Unit,
    onSolicitudTutoriaClick: () -> Unit
) {
    val estudianteTutoria by viewModel.tutorias.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppBar(onPerfilClick = onPerfilClick) },
        bottomBar = { BottomNavigationBar(onSolicitudTutoriaClick = onSolicitudTutoriaClick) }
    ) { paddingValues ->
        if (estudianteTutoria.isEmpty()) {
            EmptyStateMessage(paddingValues)
        } else {
            TutoriasList(
                tutorias = estudianteTutoria,
                onTutoriaClick = { tutoria ->
                    if (!isNavigating) {
                        isNavigating = true
                        onTutoriaEstudianteClick(tutoria)
                        isNavigating = false // Reset after navigation
                    }
                },
                paddingValues = paddingValues
            )
        }
    }
}


@Composable
fun TutoriasList(
    tutorias: List<TutoriasEs>,
    onTutoriaClick: (TutoriasEs) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        items(tutorias) { tutoria ->
            CartasTutorias(
                title = tutoria.title,
                date = tutoria.date ?: "Fecha a definir",
                location = tutoria.location ?: "Ubicación a definir",
                time = tutoria.time ?: "Hora a definir",
                link = tutoria.link,
                onClick = { onTutoriaClick(tutoria) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun EmptyStateMessage(paddingValues: PaddingValues) {
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
            IconBox()
            TutorInfo(title, date, location, time, link)
        }
    }
}

@Composable
fun IconBox() {
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
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun TutorInfo(
    title: String,
    date: String,
    location: String,
    time: String,
    link: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = date, fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (link != null) "$location $link" else location,
            fontSize = 14.sp,
            color = if (link != null) Color(0xFF007F39) else Color.Black,
            fontWeight = if (link != null) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = time, fontSize = 14.sp, color = Color.Black)
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
