package com.example.tutoriasuvg.presentation.signup

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.data.repository.FirebaseRegisterRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit,
    onNavigateToRegisterTutor: () -> Unit,
    registerRepository: FirebaseRegisterRepository
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(application, registerRepository)
    )

    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val carnet by viewModel.carnet.collectAsState()
    val year by viewModel.year.collectAsState()
    val isTutor by viewModel.isTutor.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isRegistered by viewModel.isRegistered.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val isFormValid = name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() && carnet.isNotBlank() && year.isNotBlank()
    val buttonText = if (isTutor) "Continuar" else "Registrarse"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.logo_uvg),
                        contentDescription = "Logo en letras UVG",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(bottom = 8.dp)
                    )
                }

                item {
                    Text(
                        text = "Registro",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }

                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { viewModel.onNameChanged(it) },
                        label = { Text("Nombre completo") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { viewModel.onNameChanged("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Limpiar nombre"
                                )
                            }
                        }
                    )
                }

                item {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { viewModel.onEmailChanged(it) },
                        label = { Text("Correo Institucional") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                        trailingIcon = {
                            IconButton(onClick = { viewModel.onEmailChanged("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Limpiar correo"
                                )
                            }
                        }
                    )
                }

                item {
                    OutlinedTextField(
                        value = carnet,
                        onValueChange = { viewModel.onCarnetChanged(it) },
                        label = { Text("Carnet") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                item {
                    OutlinedTextField(
                        value = year,
                        onValueChange = { viewModel.onYearChanged(it) },
                        label = { Text("Año de Estudio") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                item {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { viewModel.onPasswordChanged(it) },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        }
                    )
                }

                item {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                        label = { Text("Verifique Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (confirmPasswordVisible) "Ocultar verificación contraseña" else "Mostrar verificación contraseña"
                                )
                            }
                        }
                    )
                }

                if (errorMessage.isNotEmpty()) {
                    item {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isTutor,
                            onCheckedChange = { viewModel.onTutorChecked(it) },
                            colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            text = "¿Desea ser estudiante-tutor?",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                item {
                    Button(
                        onClick = {
                            if (isFormValid) {
                                viewModel.onRegisterClicked()
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Por favor complete todos los campos")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = isFormValid,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = buttonText,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                item {
                    Button(
                        onClick = onBackToLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(
                            text = "Volver al Login",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }

        LaunchedEffect(isRegistered) {
            if (isRegistered) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Registrado correctamente")
                    if (isTutor) {
                        onNavigateToRegisterTutor()
                    } else {
                        onBackToLogin()
                    }
                }
            }
        }
    }
}
