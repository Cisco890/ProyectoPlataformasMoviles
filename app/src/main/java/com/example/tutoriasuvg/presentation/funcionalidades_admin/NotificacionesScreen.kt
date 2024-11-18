package com.example.tutoriasuvg.presentation.funcionalidades_admin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.tutoriasuvg.data.model.Solicitud
import com.example.tutoriasuvg.data.model.Tutor
import java.util.*

@SuppressLint("DefaultLocale")
@Composable
fun AsignarTutorDialog(
    tutoresDisponibles: List<Tutor>,
    selectedTutorId: String?,
    selectedDate: String,
    selectedStartTime: String,
    selectedEndTime: String,
    selectedLocation: String,
    onTutorSelect: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Asignar Tutor") },
        text = {
            Column {
                // Lista de tutores disponibles
                tutoresDisponibles.forEach { tutor ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTutorSelect(tutor.id) }
                            .padding(8.dp)
                    ) {
                        Checkbox(
                            checked = selectedTutorId == tutor.id,
                            onCheckedChange = { isChecked ->
                                if (isChecked) onTutorSelect(tutor.id) else onTutorSelect("")
                            }
                        )
                        Text(text = tutor.name)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campos para fecha, horario y ubicación
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = onDateChange,
                    label = { Text("Fecha") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val calendar = Calendar.getInstance()
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    onDateChange("$dayOfMonth/${month + 1}/$year")
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }
                )

                OutlinedTextField(
                    value = selectedStartTime,
                    onValueChange = onStartTimeChange,
                    label = { Text("Hora de Inicio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable {
                            val calendar = Calendar.getInstance()
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    onStartTimeChange(String.format("%02d:%02d", hour, minute))
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            ).show()
                        }
                )

                OutlinedTextField(
                    value = selectedEndTime,
                    onValueChange = onEndTimeChange,
                    label = { Text("Hora de Fin") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable {
                            val calendar = Calendar.getInstance()
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    onEndTimeChange(String.format("%02d:%02d", hour, minute))
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            ).show()
                        }
                )

                OutlinedTextField(
                    value = selectedLocation,
                    onValueChange = onLocationChange,
                    label = { Text("Ubicación") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Asignar", color = Color(0xFF007F39))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
fun NotificacionCard(
    notificacion: Notificacion,
    onAsignarTutorClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF007F39))
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Icono de notificación",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = notificacion.titulo, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = notificacion.fecha ?: "Fecha a definir", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = notificacion.modalidad, fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = notificacion.hora ?: "Hora a definir", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = notificacion.estado, fontSize = 14.sp, color = Color.Black)

                if (notificacion.tutorId == null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onAsignarTutorClick() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Asignar Tutor", color = Color.White)
                    }
                } else {
                    Text(text = "Tutor asignado: ${notificacion.tutorId}", color = Color.Gray, fontSize = 14.sp)
                }
            }
        }
    }
}
