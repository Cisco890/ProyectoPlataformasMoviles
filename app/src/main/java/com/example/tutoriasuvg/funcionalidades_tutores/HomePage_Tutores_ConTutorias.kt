package com.example.tutoriasuvg.funcionalidades_tutores

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
fun HomePageTutoresWithTutorias() {
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Mostrar tarjetas de tutorías
            CardTutoria(
                title = "Ecuaciones diferenciales I",
                date = "17/09/2024",
                location = "Presencial: CIT-503",
                time = "15:00 hrs - 16:00 hrs",
                link = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            CardTutoria(
                title = "Física 3",
                date = "19/09/2024",
                location = "Virtual: ",
                time = "15:00 hrs - 16:00 hrs",
                link = "Enlace Zoom"
            )
        }
    }
}

@Composable
fun CardTutoria(
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

@Preview(showBackground = true)
@Composable
fun HomePageTutoresWithTutoriasPreview() {
    TutoriasUVGTheme {
        HomePageTutoresWithTutorias()
    }
}