package com.example.sedatedjuly.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey val id: Int,
    val text: String,
    val done: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
