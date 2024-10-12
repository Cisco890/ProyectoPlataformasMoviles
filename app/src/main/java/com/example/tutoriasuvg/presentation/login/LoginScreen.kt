package com.example.tutoriasuvg.presentation.login

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutoriasuvg.R
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme


@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateTForgotPassword: () -> Unit,
    viewModel: LoginViewModel = viewModel()
){
    val email = viewModel.email.value
    val password = viewModel.password.value
    val showError = viewModel.showError.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center

        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo_uvg),
                    contentDescription = "Logo en letras UVG",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 24.dp)
                )
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Correo Institucional")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true
                )
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Contraseña")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (showError){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                   ){
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error",
                            tint = androidx.compose.ui.graphics.Color.Red,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Contraseña o Usuario Incorrecto",
                            color = androidx.compose.ui.graphics.Color.Red,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                }

                Button(
                    onClick = { viewModel.onLoginClicked() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ){
                    Text(text = "Sign In", fontSize = 16.sp)
                }
                TextButton(
                    onClick = onNavigateToRegister,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "¿Aún no tienes cuenta? Regístrate acá",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                TextButton(
                    onClick = onNavigateTForgotPassword,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "¿Olvidaste tu contraseña",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenErrorPreview(){
//    TutoriasUVGTheme {
//        LoginScreen()
//    }
//}