package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
data class VerProgresosDestination(val route: String = "verProgresos")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerProgresosScreen(viewModel: VerProgresosViewModel = viewModel(),onBackClick: () -> Unit) {
    val progresos = viewModel.progresos.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Ver progresos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF007F39)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Progresos de los tutores
            progresos.forEach { progreso ->
                ProgresoCard(nombreTutor = progreso.nombreTutor, horasRealizadas = progreso.horasRealizadas)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ProgresoCard(nombreTutor: String, horasRealizadas: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
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
                    painter = painterResource(id = R.drawable.schedule),
                    contentDescription = "Icono de progreso",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del progreso
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = nombreTutor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Horas beca realizadas: $horasRealizadas", fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerProgresosScreenPreview() {
    TutoriasUVGTheme {
        VerProgresosScreen(onBackClick = {})
    }
}
