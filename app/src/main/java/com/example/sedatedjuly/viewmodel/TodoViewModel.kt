package com.example.sedatedjuly.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sedatedjuly.feature_todo.data.data_source.TodoDatabase
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(app: Application) : AndroidViewModel(app) {

/*    private val repo = TodoRepository(
        TodoDatabase.get(app).todoDao()
    )

    // Expose as StateFlow for Compose
    val todos = repo.todos
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private var nextId = 0

    fun add(text: String) = viewModelScope.launch {
        repo.add(ToDo(nextId++, text.trim()))
    }

    fun toggle(todo: ToDo) = viewModelScope.launch {
        repo.add(todo.copy(done = !todo.done))
    }

    fun delete(todo: ToDo) = viewModelScope.launch {
        repo.delete(todo)
    }*/
}
