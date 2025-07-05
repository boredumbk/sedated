package com.example.sedatedjuly.feature_todo.presentation.todos.components

import androidx.lifecycle.ViewModel
import com.example.sedatedjuly.feature_todo.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val todosUseCases: TodoUseCases
) : ViewModel() {

}