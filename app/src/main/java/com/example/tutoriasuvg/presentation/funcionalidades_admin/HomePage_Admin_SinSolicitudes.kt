package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.presentation.funcionalidades_tutores.AppBar
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageAdminSinSolicitudesScreen() {
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { AdminBottomNavigationBar() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes solicitudes de tutorías por asignar",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun AdminBottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF007F39)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.assignment),
                    contentDescription = "Asignar tutorías"
                )
            },
            label = { Text("Asignar tutorías") },
            selected = true,
            onClick = { /* Navegar a pantalla de asignar tutorías */ },
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
                    contentDescription = "Ver progresos"
                )
            },
            label = { Text("Ver progresos") },
            selected = false,
            onClick = { /* Navegar a pantalla de ver progresos */ },
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
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notificaciones"
                )
            },
            label = { Text("Notificaciones") },
            selected = false,
            onClick = { /* Navegar a pantalla de notificaciones */ },
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
fun HomePageAdminSinSolicitudesPreview() {
    TutoriasUVGTheme {
        HomePageAdminSinSolicitudesScreen()
    }
}