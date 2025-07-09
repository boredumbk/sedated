package com.example.sedatedjuly.feature_todo.domain.use_case

import android.util.Log
import com.example.sedatedjuly.feature_todo.domain.model.InvalidTodoException
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.repository.TodoRepository


class AddTodoUseCase(
    private val repository: TodoRepository
) {
    @Throws(InvalidTodoException::class)
    suspend operator fun invoke(todo: ToDo) {
        if (todo.title.isBlank() && todo.content.isBlank()) {
            Log.e("TodoError", "The title and description of the todo cannot be empty")
            throw InvalidTodoException("The title and description of the todo cannot be empty")
        }
        if (todo.title.isBlank()) {
            Log.e("TodoError", "The title of the todo cannot be empty")
            throw InvalidTodoException("The title of the todo cannot be empty")
        }
        if (todo.content.isBlank()) {
            Log.e("TodoError", "The description of the todo cannot be empty")
            throw InvalidTodoException("The description of the todo cannot be empty")
        }
        repository.upsertToDo(todo)


    }
}
