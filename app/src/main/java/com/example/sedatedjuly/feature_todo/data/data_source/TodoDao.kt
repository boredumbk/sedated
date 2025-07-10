package com.example.sedatedjuly.feature_todo.data.data_source

import androidx.room.*
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao  {

    @Upsert
    suspend fun upsertTodo(todo: ToDo)

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): ToDo?

    @Delete
    suspend fun deleteTodo(todo: ToDo)

    @Query("SELECT * FROM todos ORDER BY timestamp  ASC")
    fun getOldestTodos(): Flow<List<ToDo>>

    //TODOMay only need this one
    @Query("SELECT * FROM todos ORDER BY timestamp  DESC")
    fun getMostRecentTodos(): Flow<List<ToDo>>

}