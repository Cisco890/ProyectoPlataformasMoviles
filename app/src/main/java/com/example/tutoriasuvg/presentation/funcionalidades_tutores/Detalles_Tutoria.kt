package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutoriasuvg.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleTutoria(
    onBackClick: () -> Unit,
    title: String,
    date: String?,
    location: String?,
    time: String?,
    studentName: String,
    isVirtual: Boolean,
    link: String? = null,
    viewModel: DetallesTutoriaViewModel = viewModel(),
    userId: String,
    solicitudId: String
) {
    LaunchedEffect(Unit) {
        viewModel.loadSolicitudState(solicitudId)
    }

    val isCompleted = viewModel.isCompleted.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalles", color = Color.White) },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF007F39))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.today),
                        contentDescription = "Icono de tutoría",
                        modifier = Modifier.size(40.dp),
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = date ?: "Fecha a definir", fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (isVirtual && link != null) {
                        Text(
                            text = "Virtual: $link",
                            fontSize = 16.sp,
                            color = Color(0xFF007F39),
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(text = location ?: "Ubicación a definir", fontSize = 16.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = time ?: "Hora a definir", fontSize = 16.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            if (!isCompleted) {
                Button(
                    onClick = { viewModel.completarTutoria(userId, solicitudId) }, // Pass userId and solicitudId
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007F39)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(48.dp)
                        .width(200.dp)
                ) {
                    Text(text = "Tutoría Completada", color = Color.White, fontSize = 16.sp)
                }
            } else {
                Text(text = "Tutoría ya completada", color = Color.Gray, fontSize = 16.sp)
            }
        }
    }
}
