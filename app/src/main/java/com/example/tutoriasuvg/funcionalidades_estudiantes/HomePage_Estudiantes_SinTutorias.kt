package com.example.tutoriasuvg.funcionalidades_estudiantes

import androidx.compose.foundation.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@Composable
fun HomePageEstudiantesSinTutorias() {
    Scaffold(
        topBar = { AppBarEstudiantes() },
        bottomBar = { BottomNavigationBarEstudiantes() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes tutorías planificadas",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarEstudiantes() {
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
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "User Icon",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp),
                tint = Color.White
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF007F39) // Verde UVG
        )
    )
}

@Composable
fun BottomNavigationBarEstudiantes() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF007F39)), // Verde UVG
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(
            onClick = { /* No acción, solo visual */ },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Tutorías planificadas",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text("Tutorías planificadas", color = Color.White, fontSize = 12.sp)
            }
        }
        TextButton(
            onClick = { /* No acción, solo visual */ },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.post_add_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                    contentDescription = "Solicitar tutorías",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text("Solicitud de tutorías", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageEstudiantesSinTutoriasPreview() {
    TutoriasUVGTheme {
        HomePageEstudiantesSinTutorias()
    }
}
