package com.example.tutoriasuvg.funcionalidades_estudiantes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@Composable
fun SolicitudTutoria() {
    val curso = remember { mutableStateOf("") }
    val diasPreferencia = remember { mutableStateOf(setOf<String>()) }
    val jornadaPreferencia = remember { mutableStateOf("") }

    Scaffold(
        topBar = { AppBarEstudiantes() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = curso.value,
                onValueChange = { curso.value = it },
                label = { Text("Nombre del curso") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                trailingIcon = {
                    if (curso.value.isNotEmpty()) {
                        IconButton(onClick = { curso.value = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                }
            )

            Text(
                text = "Día de la semana de su preferencia",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            PreferenciaDias(diasPreferencia)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Jornada de su preferencia",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            PreferenciaJornada(jornadaPreferencia)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Acción de enviar solicitud */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007F39)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Solicitar", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun PreferenciaDias(diasPreferencia: MutableState<Set<String>>) {
    val dias = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0F2E9), RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        dias.forEach { dia ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = diasPreferencia.value.contains(dia),
                    onCheckedChange = { isChecked ->
                        diasPreferencia.value = if (isChecked) {
                            diasPreferencia.value + dia
                        } else {
                            diasPreferencia.value - dia
                        }
                    },
                    colors = CheckboxDefaults.colors(Color(0xFF6200EE))
                )
                Text(text = dia, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun PreferenciaJornada(jornadaPreferencia: MutableState<String>) {
    val jornadas = listOf("Matutina", "Vespertina")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0F2E9), RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        jornadas.forEach { jornada ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = jornadaPreferencia.value == jornada,
                    onClick = { jornadaPreferencia.value = jornada },
                    colors = RadioButtonDefaults.colors(Color(0xFF6200EE))
                )
                Text(text = jornada, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SolicitudTutoriaPreview() {
    TutoriasUVGTheme {
        SolicitudTutoria()
    }
}


