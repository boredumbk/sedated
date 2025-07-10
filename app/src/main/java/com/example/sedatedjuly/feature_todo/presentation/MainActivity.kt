package com.example.sedatedjuly.feature_todo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sedatedjuly.feature_todo.presentation.add_edit_todo.AddEditTodoScreen
import com.example.sedatedjuly.feature_todo.presentation.todos.TodosScreen
import com.example.sedatedjuly.feature_todo.presentation.util.Screen
import com.example.sedatedjuly.ui.theme.SedatedjulyTheme
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
                            route = Screen.AddEditTodosScreen.route +
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