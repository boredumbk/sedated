package com.example.sedatedjuly.data

import com.example.sedatedjuly.data.local.*
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao: TodoDao) {

    val todos: Flow<List<TodoEntity>> = dao.getAll()

    suspend fun add(todo: TodoEntity) = dao.upsert(todo)

    suspend fun delete(todo: TodoEntity) = dao.delete(todo)
}
