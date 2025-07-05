package com.example.sedatedjuly.feature_todo.data.repository

import com.example.sedatedjuly.feature_todo.data.data_source.TodoDao
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {


    override fun getTodos(): Flow<List<ToDo>> {
        return dao.getTodos()
    }

    override suspend fun getTodoById(id: Int): ToDo? {
        return dao.getTodoById(id)
    }

    override suspend fun insertTodo(todo: ToDo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: ToDo) {
        dao.deleteTodo(todo)
    }
}