package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@Composable
fun SolicitudTutoriaScreen(
    viewModel: SolicitudTutoriaViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBackClick: () -> Unit = {},
    navController: NavController
) {
    val context = LocalContext.current
    val courseName by viewModel.courseName.collectAsState()
    val selectedDays by viewModel.selectedDays.collectAsState()
    val selectedShift by viewModel.selectedShift.collectAsState()
    val message by viewModel.message.collectAsState()

    Scaffold(
        topBar = { CommonAppBar(onBackClick = onBackClick) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo para nombre del curso
            OutlinedTextField(
                value = courseName,
                onValueChange = { viewModel.updateCourseName(it) },
                label = { Text("Nombre del curso") },
                trailingIcon = {
                    if (courseName.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateCourseName("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear text"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEFEFEF), RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Preferencia de días de la semana
            Text(
                text = "Día de la semana de su preferencia",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD9EAD3), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Column {
                    selectedDays.forEach { (day, isSelected) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = day,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { viewModel.toggleDay(day) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF673AB7),
                                    uncheckedColor = Color.Gray
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Preferencia de jornada
            Text(
                text = "Jornada de su preferencia",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD9EAD3), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Column {
                    selectedShift.forEach { (shift, isSelected) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = shift,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { viewModel.selectShift(shift) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF673AB7),
                                    uncheckedColor = Color.Gray
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de "Solicitar"
            Button(
                onClick = { viewModel.solicitarTutoria() },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007F39)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Solicitar", color = Color.White, fontSize = 16.sp)
            }
        }

        // Mostrar el toast y redirigir cuando `message` cambia a un valor no nulo
        message?.let { msg ->
            LaunchedEffect(msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                if (msg == "Solicitud enviada exitosamente") {
                    navController.navigate("homePageEstudiantes") {
                        popUpTo("homePageEstudiantes") { inclusive = true }
                    }
                }
                viewModel.clearMessage()
            }
        }
    }
}
