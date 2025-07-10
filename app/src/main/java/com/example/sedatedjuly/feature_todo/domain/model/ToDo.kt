package com.example.sedatedjuly.feature_todo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sedatedjuly.ui.theme.*

@Entity(tableName = "todos")
data class ToDo(
    val title: String,
    val content: String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val timestamp: Long = System.currentTimeMillis(),
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    companion object {
        val todoColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidTodoException(message: String): Exception(message)
