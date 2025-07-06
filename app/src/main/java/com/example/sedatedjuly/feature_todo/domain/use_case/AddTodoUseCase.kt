package com.example.sedatedjuly.feature_todo.domain.use_case

import android.util.Log
import com.example.sedatedjuly.feature_todo.domain.model.InvalidTodoException
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.repository.TodoRepository


class AddTask(
    private val repository: TodoRepository
) {
    @Throws(InvalidTodoException::class)
    suspend operator fun invoke(toDo: ToDo) {
        if (toDo.title.isBlank() && toDo.content.isBlank()) {
            Log.e("TaskError", "The title and description of the task cannot be empty")
            throw InvalidTodoException("The title and description of the task cannot be empty")
        }
        if (toDo.title.isBlank()) {
            Log.e("TaskError", "The title of the task cannot be empty")
            throw InvalidTodoException("The title of the task cannot be empty")
        }
        if (toDo.content.isBlank()) {
            Log.e("TaskError", "The description of the task cannot be empty")
            throw InvalidTodoException("The description of the task cannot be empty")
        }
        repository.insertTodo(toDo)


    }
}
