package com.example.sedatedjuly.data

import com.example.sedatedjuly.feature_todo.data.data_source.TodoDao
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao: TodoDao) {

/*    val todos: Flow<List<ToDo>> = dao.getAll()

    suspend fun add(todo: ToDo) = dao.upsert(todo)

    suspend fun delete(todo: ToDo) = dao.delete(todo)*/
}
