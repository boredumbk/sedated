package com.example.sedatedjuly.feature_todo.presentation.add_edit_todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sedatedjuly.feature_todo.domain.model.InvalidTodoException
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import com.example.sedatedjuly.feature_todo.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _todoTitle = mutableStateOf(TodoTextFieldState(
        hint = "Enter todo name..."
    ))
    val todoTitle: State<TodoTextFieldState> = _todoTitle

    private val _todoContent = mutableStateOf(TodoTextFieldState(
        hint = "Enter todo description"
    ))
    val todoContent: State<TodoTextFieldState> = _todoContent

    private val _todoColor = mutableStateOf(ToDo.todoColors.random().toArgb())
    val todoColor: State<Int> = _todoColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTodoId: Int? = null

    init {
        savedStateHandle.get<Int>("todoId")?.let { todoId ->
            if(todoId != -1) {
                viewModelScope.launch {
                    todoUseCases.getTodoUseCase(todoId)?.also { todo ->
                        currentTodoId = todo.id
                        _todoTitle.value = todoTitle.value.copy(
                            text = todo.title,
                        )

                        _todoContent.value = todoContent.value.copy(
                            text = todo.content,
                        )
                        _todoColor.value = todo.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when(event) {
            is AddEditTodoEvent.EnteredTitle -> {
                _todoTitle.value = todoTitle.value.copy(
                    text = event.value
                )
            }

            //get rid below
            is AddEditTodoEvent.ChangeTitleFocus -> {
                _todoTitle.value = todoTitle.value.copy(
                    //isHintVisible = !event.focusState.isFocused &&
                    //todoTitle.value.text.isBlank()
                )
            }

            is AddEditTodoEvent.EnteredContent -> {
                _todoContent.value = todoContent.value.copy(
                    text = event.value
                )
            }

            //get rid below
            is AddEditTodoEvent.ChangeContentFocus -> {
                _todoContent.value = todoContent.value.copy(
                    //isHintVisible = !event.focusState.isFocused &&
                    //todoTitle.value.text.isBlank()
                )
            }
            is AddEditTodoEvent.ChangeColor -> {
                _todoColor.value = event.color
            }
            is AddEditTodoEvent.Savetodo -> {
                viewModelScope.launch {
                    try {
                        todoUseCases.addTodoUseCase(
                            ToDo(
                                title = todoTitle.value.text,
                                content = todoContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = todoColor.value,
                                id = currentTodoId ?: 0
                            )
                        )
                        _eventFlow.emit(UiEvent.Savetodo)

                    } catch (e: InvalidTodoException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save todo"
                            )
                        )
                    }
                }
            }
        }

    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Savetodo: UiEvent()
    }
}