package com.example.sedatedjuly.feature_todo.domain.use_case

import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.repository.TodoRepository
import com.example.sedatedjuly.feature_todo.domain.util.OrderType
import com.example.sedatedjuly.feature_todo.domain.util.TodoOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodosUseCase(
    private val repository: TodoRepository
) {
    operator fun invoke(
        todoOrder: TodoOrder = TodoOrder.Date(OrderType.Descending)
    ): Flow<List<ToDo>> {
        return repository.getMostRecentToDos().map { todos ->
            when(todoOrder.orderType) {
                is OrderType.Ascending -> {
                    when(todoOrder) {
                        is TodoOrder.Title -> todos.sortedBy { it.title.lowercase() }
                        is TodoOrder.Date -> todos.sortedBy { it.timestamp }
                        is TodoOrder.Color -> todos.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(todoOrder) {
                        is TodoOrder.Title -> todos.sortedByDescending { it.title.lowercase() }
                        is TodoOrder.Date -> todos.sortedByDescending { it.timestamp }
                        is TodoOrder.Color -> todos.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}