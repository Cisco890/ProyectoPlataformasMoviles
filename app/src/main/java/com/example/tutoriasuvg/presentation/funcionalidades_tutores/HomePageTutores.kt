package com.example.tutoriasuvg.presentation.funcionalidades_tutores

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
import com.example.tutoriasuvg.data.repository.SolicitudRepository
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme
import kotlinx.serialization.Serializable

@Composable
fun HomePageTutores(
    viewModel: HomePageTutoresViewModel = viewModel(),
    onTutoriaClick: (Tutoria) -> Unit,
    onProgresoClick: () -> Unit
) {
    val tutorias = viewModel.tutorias.collectAsState().value
    Scaffold(
        topBar = { AppBar(onProgresoClick = onProgresoClick) },
    ) { paddingValues ->
        if (tutorias.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tienes tutorías asignadas",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(tutorias) { tutoria ->
                    CardTutoria(
                        title = tutoria.title,
                        date = tutoria.date ?: "Fecha no definida",
                        location = tutoria.location ?: "Ubicación no definida",
                        time = tutoria.time ?: "Hora no definida",
                        link = tutoria.link,
                        onClick = { onTutoriaClick(tutoria) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun CardTutoria(
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onProgresoClick: () -> Unit) {
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
            IconButton(onClick = onProgresoClick) {
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

@Preview(showBackground = true)
@Composable
fun HomePageConTutoresPreview() {
    val mockSolicitudRepository = SolicitudRepository()

    val mockViewModel = HomePageTutoresViewModel(
        solicitudRepository = mockSolicitudRepository,
        tutorId = "sampleTutorId"
    )

    TutoriasUVGTheme {
        HomePageTutores(
            viewModel = mockViewModel,
            onTutoriaClick = { /* Acción al hacer clic en la tutoría */ },
            onProgresoClick = { /* Acción al hacer clic en progreso */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageTutoresSinTutoriasPreview() {
    val mockSolicitudRepository = SolicitudRepository()

    val mockViewModel = HomePageTutoresViewModel(
        solicitudRepository = mockSolicitudRepository,
        tutorId = "sampleTutorId"
    )

    TutoriasUVGTheme {
        HomePageTutores(
            viewModel = mockViewModel,
            onTutoriaClick = { /* Acción al hacer clic en la tutoría */ },
            onProgresoClick = { /* Acción al hacer clic en progreso */ }
        )
    }
}
