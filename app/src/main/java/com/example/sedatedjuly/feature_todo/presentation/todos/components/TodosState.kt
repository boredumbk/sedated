package com.example.sedatedjuly.feature_todo.presentation.todos.components

import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.util.OrderType
import com.example.sedatedjuly.feature_todo.domain.util.TodoOrder

data class TodosState(
    val todos: List<ToDo> = emptyList(),
    val todoOrder: TodoOrder = TodoOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
