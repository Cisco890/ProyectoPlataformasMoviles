package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tutoriasuvg.presentation.signup.RegisterStudentTutorScreen
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TutoriasUVGTheme {
                RegisterStudentTutorScreen()
                }
            }
        }
    }

