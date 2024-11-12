package com.example.tutoriasuvg.presentation.funcionalidades_estudiantes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@Composable
fun PerfilEstudianteScreen(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onBecomeTutorClick: () -> Unit,
    viewModel: PerfilEstudianteViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val estudiante = viewModel.estudiante.collectAsState().value
    val isTutor = viewModel.isTutor.collectAsState().value

    Scaffold(
        topBar = { PerfilAppBar(onBackClick) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF007F39), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.person),
                        contentDescription = "Perfil",
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${estudiante.nombre}\n${estudiante.carnet}\n${estudiante.anio}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (!isTutor) {
                    Button(
                        onClick = { onBecomeTutorClick() }, // Use onBecomeTutorClick
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007F39)),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(48.dp)
                    ) {
                        Text(text = "Volverme Tutor", color = Color.White, fontSize = 16.sp)
                    }
                } else {
                    Text(text = "Ya eres tutor", color = Color.Gray, fontSize = 16.sp)
                }
            }

            // Logout button at the bottom
            Button(
                onClick = onLogoutClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(bottom = 16.dp)
                    .height(48.dp)
            ) {
                Text(text = "Cerrar sesión", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Perfil", color = Color.White, fontSize = 20.sp)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF007F39)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PerfilEstudianteScreenPreview() {
    val loginRepository = FirebaseLoginRepository()
    val registerRepository = FirebaseRegisterRepository()

    TutoriasUVGTheme {
        PerfilEstudianteScreen(
            onBackClick = {},
            onLogoutClick = {},
            onBecomeTutorClick = {},
            viewModel = PerfilEstudianteViewModel(loginRepository, registerRepository)
        )
    }
}


