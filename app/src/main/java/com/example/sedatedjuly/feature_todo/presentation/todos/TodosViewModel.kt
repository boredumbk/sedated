package com.example.sedatedjuly.feature_todo.presentation.todos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.use_case.TodoUseCases
import com.example.sedatedjuly.feature_todo.domain.util.OrderType
import com.example.sedatedjuly.feature_todo.domain.util.TodoOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val todosUseCases: TodoUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TodosState())
    val state: State<TodosState> = _state
    private var recentlyDeletedTodo: ToDo? = null

    private var getTodosJob: Job? = null

    init {
        getTodos(TodoOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TodosEvent) {
        when(event) {
            is TodosEvent.Order -> {
                if(state.value.todoOrder::class == event.todoOrder::class &&
                    state.value.todoOrder.orderType == event.todoOrder.orderType
                ) {
                    return
                }
                getTodos(event.todoOrder)
            }
            is TodosEvent.DeleteTodo -> {
                viewModelScope.launch {
                    todosUseCases.deleteTodoUseCase(event.todo)
                    recentlyDeletedTodo = event.todo
                }
            }
            is TodosEvent.RestoreTodo -> {
                viewModelScope.launch {
                    todosUseCases.addTodoUseCase(recentlyDeletedTodo ?: return@launch)
                    recentlyDeletedTodo = null
                }
            }
            is TodosEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getTodos(todoOrder: TodoOrder) {
        getTodosJob?.cancel()
        getTodosJob = todosUseCases.getTodosUseCase(todoOrder)
            .onEach { todos ->
                _state.value = state.value.copy(
                    todos = todos,
                    todoOrder = todoOrder
                )
            }
            .launchIn(viewModelScope)
    }
}