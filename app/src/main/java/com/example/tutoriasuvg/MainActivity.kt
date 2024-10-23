package com.example.tutoriasuvg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.tutoriasuvg.navigation.NavGraph
import com.example.tutoriasuvg.ui.theme.TutoriasUVGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutoriasUVGTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
