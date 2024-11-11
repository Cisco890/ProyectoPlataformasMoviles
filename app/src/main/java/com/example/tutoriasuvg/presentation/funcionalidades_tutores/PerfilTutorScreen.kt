package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilTutorScreen(
    profileData: PerfilTutorData,
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit,
    onLoginNavigate: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Icon",
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Nombre: ${profileData.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    "Carnet: ${profileData.carnet}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    "Año: ${profileData.year}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Total ${profileData.hours}/${profileData.totalHours}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                CircularProgressBar(
                    progress = profileData.progress,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.weight(1f)) // Empuja el contenido hacia la parte superior

                Button(
                    onClick = onLoginNavigate, // Cambia el onClick para llamar a la función de navegación
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Cerrar sesión", color = MaterialTheme.colorScheme.onError)
                }
            }
        }
    )
}

@Composable
fun CircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            progress = progress,
            color = color,
            strokeWidth = 8.dp,
            modifier = Modifier.size(80.dp)
        )
        Text(
            text = "${(progress * 100).toInt()}%",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
