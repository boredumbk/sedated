package com.example.sedatedjuly.feature_todo.domain.use_case

data class TodoUseCases(
    val getTodosUseCase: GetTodosUseCase,
    val deleteTodoUseCase: DeleteTodoUseCase,
    val addTodoUseCase: AddTodoUseCase,
    val getTodoUseCase: GetTodoUseCase
) {
}