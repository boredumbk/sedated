package com.example.sedatedjuly.feature_todo.presentation.add_edit_todo.components

import androidx.compose.ui.focus.FocusState

sealed class AddEditTaskEvent {
    data class EnteredTitle(val value: String): AddEditTaskEvent()

    //get rid below
    data class ChangeTitleFocus(val focusState: FocusState): AddEditTaskEvent()
    data class EnteredContent(val value: String): AddEditTaskEvent()

    //get rid below
    data class ChangeContentFocus(val focusState: FocusState): AddEditTaskEvent()
    data class ChangeColor(val color: Int): AddEditTaskEvent()
    object SaveTask: AddEditTaskEvent()

}
