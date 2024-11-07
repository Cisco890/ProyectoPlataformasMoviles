package com.example.tutoriasuvg.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutoriasuvg.R

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginSuccess: (String) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                    contentDescription = "Logo UVG",
                    modifier = Modifier.size(150.dp).padding(bottom = 24.dp)
                )

                // Campo de Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Correo Institucional") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )

                // Campo de Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                // Botón de Iniciar Sesión
                Button(
                    onClick = { viewModel.onLoginClicked(onLoginSuccess) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = if (isLoading) "Cargando..." else "Iniciar Sesión", fontSize = 16.sp)
                }

                TextButton(onClick = onNavigateToRegister, modifier = Modifier.padding(top = 16.dp)) {
                    Text("¿Aún no tienes cuenta? Regístrate acá")
                }

                TextButton(onClick = onNavigateToForgotPassword, modifier = Modifier.padding(top = 8.dp)) {
                    Text("¿Olvidaste tu contraseña?")
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = androidx.compose.ui.graphics.Color(0xFF007F39))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Cargando", fontSize = 16.sp)
    }
}
