package com.example.tutoriasuvg.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@Composable
fun RegisterStudentTutorScreen(){
    var year by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }

    val courses = listOf(
        "Pensamiento Cuantitativo", "Ciencias de la Vida", "Comunicación Efectiva",
        "Química General", "Algoritmos y Programación Básica", "Cálculo 1",
        "Física 1", "Álgebra Lineal", "Estadística 1", "Ciudadanía Global",
        "Programación Orientada a Objetos", "Cálculo 2", "Física 2",
        "Algoritmos y Estructuras de Datos", "Organización de Computadoras y Assambler",
        "Guatemala en el Contexto Mundial", "Retos Ambientales y Sostenibilidad",
        "Física 3", "Ecuaciones Diferenciales 1", "Investigación y Pensamiento Cuantitativo",
        "Matemática Discreta 1", "Programación de Microprocesadores",
        "Programación de Aplicaciones Móviles"
    )

    val selectedCourses = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.letras_uvg),
                    contentDescription = "Logo en letras UVG",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Registro Estudiante-tutor",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("Año estudiantil") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { year = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Limpiar año"
                            )
                        }
                    }
                )
                Text(
                    text = "Cursos con calificación mayor a 85",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(courses) { course ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            ) {
                                Checkbox(
                                    checked = selectedCourses[course] ?: false,
                                    onCheckedChange = { isChecked ->
                                        selectedCourses[course] = isChecked
                                    },
                                    colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                                )
                                Text(
                                    text = course,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
                OutlinedTextField(
                    value = hours,
                    onValueChange = { hours = it },
                    label = { Text("Horas beca necesarias por semestre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { hours = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Limpiar horas"
                            )
                        }
                    }
                )
                Button(
                    onClick = { /* Lógica para registrarse */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Registrarse",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
            }
        }
    } }
}

@Preview(showBackground = true)
@Composable
fun RegisterStudentTutorScreenPreview() {
    TutoriasUVGTheme {
        RegisterStudentTutorScreen()
    }
}