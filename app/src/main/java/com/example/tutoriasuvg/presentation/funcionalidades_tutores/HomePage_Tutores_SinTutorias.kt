package com.example.tutoriasuvg.presentation.funcionalidades_tutores

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
fun HomePageTutores() {
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes tutorías asignadas",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
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
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF007F39)
        )
    )
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF007F39)
    ) {
        NavigationBarItem(
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "Tutorías planificadas")
            },
            label = { Text("Tutorías planificadas") },
            selected = true,
            onClick = { /* Navegar a otras pantallas */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.schedule),
                    contentDescription = "Horas beca"
                )
            },
            label = { Text("Horas beca") },
            selected = false,
            onClick = { /* Navegar a otras pantallas */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageTutoresPreview() {
    TutoriasUVGTheme {
        HomePageTutores()
    }
}
