package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.data.model.Solicitud
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageAdmin(
    viewModel: NotificacionesViewModel,
    onProfileClick: () -> Unit // Navegación al perfil del administrador
) {
    Scaffold(
        topBar = { AdminAppBar(onProfileClick = onProfileClick) }
    ) { paddingValues ->
        val notificaciones by viewModel.notificaciones.collectAsState()
        val tutoresDisponibles by viewModel.tutoresDisponibles.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        var showDialog by remember { mutableStateOf(false) }
        var currentSolicitud by remember { mutableStateOf<Solicitud?>(null) }
        var selectedDate by remember { mutableStateOf("") }
        var selectedStartTime by remember { mutableStateOf("") }
        var selectedEndTime by remember { mutableStateOf("") }
        var selectedLocation by remember { mutableStateOf("") }
        var selectedTutorId by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            viewModel.cargarNotificaciones()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (notificaciones.isEmpty()) {
                // Mensaje cuando no hay notificaciones
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay notificaciones disponibles",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                // Mostrar lista de notificaciones
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notificaciones) { notificacion ->
                        NotificacionCard(
                            notificacion = notificacion,
                            onAsignarTutorClick = {
                                currentSolicitud = notificacion.solicitud
                                viewModel.obtenerTutoresParaCurso(notificacion.titulo)
                                showDialog = true
                            }
                        )
                    }
                }
            }

            // Dialogo para asignar tutor
            if (showDialog) {
                AsignarTutorDialog(
                    tutoresDisponibles = tutoresDisponibles,
                    selectedTutorId = selectedTutorId,
                    selectedDate = selectedDate,
                    selectedStartTime = selectedStartTime,
                    selectedEndTime = selectedEndTime,
                    selectedLocation = selectedLocation,
                    onTutorSelect = { selectedTutorId = it },
                    onDateChange = { selectedDate = it },
                    onStartTimeChange = { selectedStartTime = it },
                    onEndTimeChange = { selectedEndTime = it },
                    onLocationChange = { selectedLocation = it },
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        coroutineScope.launch {
                            currentSolicitud?.let { solicitud ->
                                viewModel.asignarTutor(
                                    solicitud = solicitud,
                                    tutorId = selectedTutorId ?: return@launch,
                                    date = selectedDate,
                                    location = selectedLocation,
                                    time = "$selectedStartTime - $selectedEndTime"
                                )
                            }
                            selectedTutorId = null
                            selectedDate = ""
                            selectedStartTime = ""
                            selectedEndTime = ""
                            selectedLocation = ""
                            showDialog = false
                        }
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAppBar(onProfileClick: () -> Unit) {
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
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Perfil de Administrador",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF007F39))
    )
}
