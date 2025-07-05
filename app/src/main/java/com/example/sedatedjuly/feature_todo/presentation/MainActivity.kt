package com.example.sedatedjuly.feature_todo.presentation   // ← keep this the same as the other files

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sedatedjuly.ui.screens.TodoScreen
import com.example.sedatedjuly.ui.theme.SedatedjulyTheme   // ← adjust if your Theme.kt
//    defines a different name

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SedatedjulyTheme {   // or SedatedJulyTheme, etc.
                TodoScreen()
            }
        }
    }
}
