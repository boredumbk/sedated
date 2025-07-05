package com.example.sedatedjuly.feature_todo.domain.repository

import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getTodos(): Flow<List<ToDo>>

    suspend fun getTodoById(id: Int): ToDo?

    suspend fun insertTodo(todo: ToDo)

    suspend fun deleteTodo(todo: ToDo)
}