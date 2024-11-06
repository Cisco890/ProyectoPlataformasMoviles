package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.presentation.funcionalidades_estudiantes.CommonAppBar
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@Composable
fun SolicitudTutoriaScreen(
    onBackClick: () -> Unit = {}, // Add onBackClick parameter
    viewModel: SolicitudTutoriaViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val courseName by viewModel.courseName.collectAsState()
    val selectedDays by viewModel.selectedDays.collectAsState()
    val selectedShift by viewModel.selectedShift.collectAsState()

    Scaffold(
        topBar = { CommonAppBar(onBackClick = onBackClick) } // Pass onBackClick to CommonAppBar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Course name input field
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

            // Preferred days of the week
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

            // Preferred shift
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

            // "Solicitar" button
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
    }
}



@Preview(showBackground = true)
@Composable
fun SolicitudTutoriaScreenPreview() {
    TutoriasUVGTheme {
        SolicitudTutoriaScreen()
    }
}