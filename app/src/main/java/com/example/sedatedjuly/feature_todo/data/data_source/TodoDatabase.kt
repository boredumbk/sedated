package com.example.sedatedjuly.feature_todo.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sedatedjuly.feature_todo.domain.model.ToDo

@Database(
    entities = [ToDo::class],
    version = 1,
)
abstract class TodoDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "todos_db"
    }
}
