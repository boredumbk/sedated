package com.example.sedatedjuly.feature_todo.presentation.util

sealed class Screen(val route: String) {
    object TodosScreen: Screen("todos_screen")
    object AddEditTodosScreen: Screen("add_edit_todos_screen")
}