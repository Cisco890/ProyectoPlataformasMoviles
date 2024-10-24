package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgresoHorasBeca(
    onBackClick: () -> Unit,
    nombreEstudiante: String,
    carnetEstudiante: String,
    anioEstudiante: String,
    porcentajeProgreso: Float,
    totalHoras: Int,
    horasCompletadas: Int
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil", color = Color.White) },
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
        // Contenido principal de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFF007F39), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Perfil de Estudiante",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Información del estudiante
            Text(text = nombreEstudiante, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = carnetEstudiante, fontSize = 16.sp, color = Color.Black)
            Text(text = anioEstudiante, fontSize = 16.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Total $horasCompletadas/$totalHoras",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de progreso circular
            CircularProgressBar(
                percentage = porcentajeProgreso,
                displayPercentage = (porcentajeProgreso * 100).toInt()
            )
        }
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    displayPercentage: Int,
    radius: Float = 100f,
    color: Color = Color(0xFF007F39),
    strokeWidth: Float = 16f
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius.dp * 2)
    ) {
        // Barra de progreso circular
        Canvas(modifier = Modifier.size(radius.dp * 2)) {
            drawCircle(
                color = Color.LightGray,
                radius = radius,
                style = Stroke(strokeWidth)
            )
            // Progreso circular
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * percentage,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
        // Texto del porcentaje en el centro
        Text(
            text = "$displayPercentage%",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgresoHorasBecaPreview() {
    TutoriasUVGTheme {
        ProgresoHorasBeca(
            onBackClick = {},
            nombreEstudiante = "Nombre del estudiante",
            carnetEstudiante = "Carnet del estudiante",
            anioEstudiante = "Año que cursa el Estudiante",
            porcentajeProgreso = 0.75f,
            totalHoras = 35,
            horasCompletadas = 25
        )
    }
}
