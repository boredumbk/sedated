package com.example.sedatedjuly.feature_todo.domain.use_case

import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.repository.TodoRepository

class DeleteTodoUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(toDo: ToDo) {
        repository.deleteToDo(toDo)
    }
}