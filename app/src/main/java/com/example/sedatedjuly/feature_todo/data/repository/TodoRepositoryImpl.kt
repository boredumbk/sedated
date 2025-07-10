package com.example.sedatedjuly.feature_todo.data.repository

import com.example.sedatedjuly.feature_todo.data.data_source.TodoDao
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun upsertToDo(todo: ToDo) {
        dao.upsertTodo(todo)  // <- Updated method name
    }

    override suspend fun getToDoById(id: Int): ToDo? {
        return dao.getTodoById(id)  // <- Updated method name
    }

    override suspend fun deleteToDo(todo: ToDo) {
        dao.deleteTodo(todo)
    }

    override fun getOldestToDos(): Flow<List<ToDo>> {
        return dao.getOldestTodos()  // <- Updated method name
    }

    //may only need this one
    override fun getMostRecentToDos(): Flow<List<ToDo>> {
        return dao.getMostRecentTodos()  // <- Updated method name
    }
}