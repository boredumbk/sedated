package com.example.sedatedjuly.feature_todo.presentation.add_edit_todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    todoUseCases.gettodo(todoId)?.also { todo ->
                        currentTodoId = todo.id
                        _todoTitle.value = todoTitle.value.copy(
                            text = todo.todoName,
                        )

                        _todoContent.value = todoContent.value.copy(
                            text = todo.todoDescription,
                        )
                        _todoColor.value = todo.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEdittodoEvent) {
        when(event) {
            is AddEdittodoEvent.EnteredTitle -> {
                _todoTitle.value = todoTitle.value.copy(
                    text = event.value
                )
            }

            //get rid below
            is AddEdittodoEvent.ChangeTitleFocus -> {
                _todoTitle.value = todoTitle.value.copy(
                    //isHintVisible = !event.focusState.isFocused &&
                    //todoTitle.value.text.isBlank()
                )
            }

            is AddEdittodoEvent.EnteredContent -> {
                _todoContent.value = todoContent.value.copy(
                    text = event.value
                )
            }

            //get rid below
            is AddEdittodoEvent.ChangeContentFocus -> {
                _todoContent.value = todoContent.value.copy(
                    //isHintVisible = !event.focusState.isFocused &&
                    //todoTitle.value.text.isBlank()
                )
            }
            is AddEdittodoEvent.ChangeColor -> {
                _todoColor.value = event.color
            }
            is AddEdittodoEvent.Savetodo -> {
                viewModelScope.launch {
                    try {
                        todoUseCases.addtodo(
                            todo(
                                todoName = todoTitle.value.text,
                                todoDescription = todoContent.value.text,
                                todoCreatedAt = System.currentTimeMillis(),
                                color = todoColor.value,
                                id = currenttodoId ?: 0

                            )
                        )
                        _eventFlow.emit(UiEvent.Savetodo)

                    } catch (e: InvalidtodoException) {
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