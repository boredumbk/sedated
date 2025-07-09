package com.example.sedatedjuly.feature_todo.presentation.add_edit_todo.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _taskTitle = mutableStateOf(TaskTextFieldState(
        hint = "Enter task name..."
    ))
    val taskTitle: State<TaskTextFieldState> = _taskTitle

    private val _taskContent = mutableStateOf(TaskTextFieldState(
        hint = "Enter task description"
    ))
    val taskContent: State<TaskTextFieldState> = _taskContent

    private val _taskColor = mutableStateOf(Task.taskColors.random().toArgb())
    val taskColor: State<Int> = _taskColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if(taskId != -1) {
                viewModelScope.launch {
                    taskUseCases.getTask(taskId)?.also { task ->
                        currentTaskId = task.id
                        _taskTitle.value = taskTitle.value.copy(
                            text = task.taskName,
                        )

                        _taskContent.value = taskContent.value.copy(
                            text = task.taskDescription,
                        )
                        _taskColor.value = task.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when(event) {
            is AddEditTaskEvent.EnteredTitle -> {
                _taskTitle.value = taskTitle.value.copy(
                    text = event.value
                )
            }

            //get rid below
            is AddEditTaskEvent.ChangeTitleFocus -> {
                _taskTitle.value = taskTitle.value.copy(
                    //isHintVisible = !event.focusState.isFocused &&
                    //taskTitle.value.text.isBlank()
                )
            }

            is AddEditTaskEvent.EnteredContent -> {
                _taskContent.value = taskContent.value.copy(
                    text = event.value
                )
            }

            //get rid below
            is AddEditTaskEvent.ChangeContentFocus -> {
                _taskContent.value = taskContent.value.copy(
                    //isHintVisible = !event.focusState.isFocused &&
                    //taskTitle.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.ChangeColor -> {
                _taskColor.value = event.color
            }
            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch {
                    try {
                        taskUseCases.addTask(
                            Task(
                                taskName = taskTitle.value.text,
                                taskDescription = taskContent.value.text,
                                taskCreatedAt = System.currentTimeMillis(),
                                color = taskColor.value,
                                id = currentTaskId ?: 0

                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTask)

                    } catch (e: InvalidTaskException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save task"
                            )
                        )
                    }
                }
            }
        }

    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveTask: UiEvent()
    }
}