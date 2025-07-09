package com.example.sedatedjuly.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sedatedjuly.ui.components.TodoRow
import com.example.sedatedjuly.viewmodel.TodoViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {


    var entry by remember { mutableStateOf("") }
    val listState   = rememberLazyListState()             // <─ for scrolling
    val coroutine   = rememberCoroutineScope()
    val inner = 10.dp


}

@Composable
private fun InputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: (String) -> Unit,
) {
    val corner = 22.dp

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(), // Only the input bar responds to keyboard
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                placeholder = { Text("Add a todo…") },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 56.dp, max = 120.dp), // Reduced max height
                shape = RoundedCornerShape(corner),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                singleLine = false,
                minLines = 1,
                maxLines = 3, // Reduced from 5 to 3
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (text.isNotBlank()) {
                            onSend(text.trim())
                        }
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (text.isNotBlank()) {
                                onSend(text.trim())
                            }
                        }
                    ) {
                        Icon( Icons.AutoMirrored.Filled.Send, "Send")
                    }
                }
            )
        }
    }
}