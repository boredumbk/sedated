package com.example.sedatedjuly.feature_todo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sedatedjuly.ui.theme.DeepBlue
import com.example.sedatedjuly.ui.theme.ForestGreen
import com.example.sedatedjuly.ui.theme.GoldenYellow
import com.example.sedatedjuly.ui.theme.SlateGray
import com.example.sedatedjuly.ui.theme.SunsetOrange

@Entity()
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
        val noteColors = listOf(DeepBlue, ForestGreen, SunsetOrange, GoldenYellow, SlateGray)
    }
}

class InvalidTodoException(message: String): Exception(message)
