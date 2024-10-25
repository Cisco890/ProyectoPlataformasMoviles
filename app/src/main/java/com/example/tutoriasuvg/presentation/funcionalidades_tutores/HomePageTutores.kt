package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import HomePageTutoresViewModel
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

@Serializable
data class Tutoria(
    val title: String,
    val date: String,
    val location: String,
    val time: String,
    val link: String?
)

@Composable
fun HomePageTutores(
    viewModel: HomePageTutoresViewModel,
    onTutoriaClick: (Tutoria) -> Unit,
    onProgresoClick: () -> Unit
) {
    val tutorias = viewModel.tutorias.collectAsState().value
    Scaffold(
        topBar = { AppBar(onProgresoClick = onProgresoClick) },
    ) { paddingValues ->
        if (tutorias.isEmpty()) {
            // Mostrar mensaje cuando no hay tutorías
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
            // Mostrar la lista de tutorías asignadas
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                tutorias.forEach { tutoria ->
                    CardTutoria(
                        title = tutoria.title,
                        date = tutoria.date,
                        location = tutoria.location,
                        time = tutoria.time,
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
            // Ícono de calendario dentro de un círculo verde
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

            // Información de la tutoría
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = date, fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))

                // Mostrar la ubicación con el link (si está disponible)
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
            IconButton(onClick = { onProgresoClick() }) {
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
    val mockViewModel = HomePageTutoresViewModel(
        tutoriasIniciales = listOf(
            Tutoria(
                title = "Ecuaciones diferenciales I",
                date = "17/09/2024",
                location = "Presencial: CIT-503",
                time = "15:00 hrs - 16:00 hrs",
                link = null
            ),
            Tutoria(
                title = "Física 3",
                date = "19/09/2024",
                location = "Virtual: ",
                time = "15:00 hrs - 16:00 hrs",
                link = "Enlace Zoom"
            )
        )
    )

    TutoriasUVGTheme {
        HomePageTutores(
            viewModel = mockViewModel,
            onTutoriaClick = { /* */ },
            onProgresoClick = { /* */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageTutoresSinTutoriasPreview() {
    val mockViewModel = HomePageTutoresViewModel(tutoriasIniciales = emptyList())

    TutoriasUVGTheme {
        HomePageTutores(
            viewModel = mockViewModel,
            onTutoriaClick = { /* */ },
            onProgresoClick = { /**/ }
        )
    }
}
