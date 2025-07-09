package com.example.sedatedjuly.feature_todo.presentation   // ← keep this the same as the other files

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.sedatedjuly.feature_todo.presentation.util.Screen
import com.example.sedatedjuly.ui.screens.TodoScreen
import com.example.sedatedjuly.ui.theme.SedatedjulyTheme   // ← adjust if your Theme.kt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SedatedjulyTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.TodosScreen.route
                    ) {
                        composable(route = Screen.TodosScreen.route) {
                            TodosScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditTodoScreen.route +
                                    "?todoId={todoId}&todoColor={todoColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "todoId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "todoColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("todoColor") ?: -1
                            AddEditTodoScreen(
                                navController = navController,
                                todoColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}

