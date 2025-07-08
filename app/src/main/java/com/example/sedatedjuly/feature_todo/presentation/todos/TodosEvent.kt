package com.example.sedatedjuly.feature_todo.presentation.todos

import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.util.TodoOrder

sealed class TodosEvent {
    data class Order(val todoOrder: TodoOrder): TodosEvent()
    data class DeleteTodo(val todo: ToDo): TodosEvent()
    object RestoreTodo: TodosEvent()
    object ToggleOrderSection: TodosEvent()
}