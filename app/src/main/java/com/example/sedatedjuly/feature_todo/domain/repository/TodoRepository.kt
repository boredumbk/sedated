package com.example.sedatedjuly.feature_todo.domain.repository

import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun upsertToDo(todo: ToDo)

    suspend fun getToDoById(id: Int): ToDo?

    suspend fun deleteToDo(todo: ToDo)

    fun getOldestToDos(): Flow<List<ToDo>>

    //may only need this one
    fun getMostRecentToDos(): Flow<List<ToDo>>
}