package com.example.tutoriasuvg.presentation.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tutoriasuvg.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(onBackToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_uvg),
                    contentDescription = "Logo en letras UVG",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 24.dp)
                )

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Institucional") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        // Validar que el campo de correo no esté vacío
                        if (email.isNotEmpty()) {
                            auth.sendPasswordResetEmail(email)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Muestra un mensaje de éxito al usuario
                                        Toast.makeText(
                                            context,
                                            "Correo de recuperación enviado. Revisa tu bandeja de entrada.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        // Regresa a la pantalla de inicio de sesión
                                        onBackToLogin()
                                    } else {
                                        // Muestra un mensaje de error si falla
                                        Toast.makeText(
                                            context,
                                            "Error al enviar el correo de recuperación.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        } else {
                            // Muestra un mensaje de advertencia si el campo está vacío
                            Toast.makeText(
                                context,
                                "Por favor ingresa tu correo institucional.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Enviar correo de recuperación")
                }

                Button(
                    onClick = onBackToLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Volver al Inicio de Sesión")
                }
            }
        }
    }
}
