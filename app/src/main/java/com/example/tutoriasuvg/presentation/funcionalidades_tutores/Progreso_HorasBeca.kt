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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProgresoHorasBecaRoute(
    uiState: ProgresoHorasBecaUIState,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    ProgresoHorasBecaScreen(
        isLoading = uiState.isLoading,
        nombreEstudiante = uiState.nombreEstudiante,
        carnetEstudiante = uiState.carnetEstudiante,
        anioEstudio = uiState.anioEstudio,
        horasCompletadas = uiState.horasCompletadas,
        totalHoras = uiState.totalHoras,
        porcentajeProgreso = uiState.porcentajeProgreso,
        onBackClick = onBackClick,
        onLogoutClick = onLogoutClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgresoHorasBecaScreen(
    isLoading: Boolean,
    nombreEstudiante: String,
    carnetEstudiante: String,
    anioEstudio: String,
    horasCompletadas: Int,
    totalHoras: Int,
    porcentajeProgreso: Float,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF007F39))
            }
        } else {
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

                Text(text = "Nombre: $nombreEstudiante", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Carnet: $carnetEstudiante", fontSize = 16.sp, color = Color.Black)
                Text(text = "Año: $anioEstudio", fontSize = 16.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Total $horasCompletadas/$totalHoras",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircularProgressBar(
                    percentage = porcentajeProgreso,
                    displayPercentage = (porcentajeProgreso * 100).toInt()
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onLogoutClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(text = "Cerrar sesión", color = Color.White, fontSize = 16.sp)
                }
            }
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
        Canvas(modifier = Modifier.size(radius.dp * 2)) {
            drawCircle(
                color = Color.LightGray,
                radius = radius,
                style = Stroke(strokeWidth)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * percentage,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
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
fun ProgresoHorasBecaScreenPreview() {
    ProgresoHorasBecaScreen(
        isLoading = false,
        nombreEstudiante = "Juan Pérez",
        carnetEstudiante = "23123",
        anioEstudio = "3",
        horasCompletadas = 50,
        totalHoras = 100,
        porcentajeProgreso = 0.5f,
        onBackClick = {},
        onLogoutClick = {}
    )
}


