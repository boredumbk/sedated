package com.example.sedatedjuly.feature_todo.data.data_source

import androidx.room.*
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao  {

    @Upsert
    suspend fun upsertTask(task: ToDo)

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTaskById(id: Int): ToDo?

    @Delete
    suspend fun deleteTodo(todo: ToDo)

    @Query("SELECT * FROM todos ORDER BY taskCreatedAt ASC")
    fun getOldestTasks(): Flow<List<ToDo>>

    //TODOMay only need this one
    @Query("SELECT * FROM todos ORDER BY taskCreatedAt DESC")
    fun getMostRecentTasks(): Flow<List<ToDo>>

}