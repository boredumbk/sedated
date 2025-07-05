package com.example.sedatedjuly.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sedatedjuly.ui.theme.DeepBlue
import com.example.sedatedjuly.ui.theme.ForestGreen
import com.example.sedatedjuly.ui.theme.GoldenYellow
import com.example.sedatedjuly.ui.theme.SlateGray
import com.example.sedatedjuly.ui.theme.SunsetOrange

@Entity(tableName = "todos")
data class ToDo(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(DeepBlue, ForestGreen, SunsetOrange, GoldenYellow, SlateGray)
    }
}
