package com.example.sedatedjuly.feature_todo.presentation.add_edit_todo

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sedatedjuly.core.util.TestTags
import com.example.sedatedjuly.feature_todo.domain.model.ToDo
import kotlinx.coroutines.launch

@Composable
fun AddEditTodoScreen (
    navController: NavController,
    todoColor: Int,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val titleState = viewModel.todoTitle.value
    val contentState = viewModel.todoContent.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val todoBackgroundAnimatable = remember {
        Animatable(
            Color(if(todoColor != -1) todoColor else viewModel.todoColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditTodoViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditTodoViewModel.UiEvent.SaveTodo -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTodoEvent.SaveTodo)
                },
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(todoBackgroundAnimatable.value)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ToDo.todoColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.todoColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    todoBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditTodoEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

//            TextField(
//                value = titleState.text,
//                onValueChange = { newValue ->
//                    viewModel.onEvent(AddEditTodoEvent.EnteredTitle(newValue))
//                },
//                label = { Text(titleState.hint) }, // Assuming titleState.hint contains the hint text
//                textStyle = TextStyle(fontSize = 20.sp),
//                modifier = Modifier
//                    .padding(horizontal = 16.dp, vertical = 16.dp)
//                    .fillMaxWidth(),
//
//            )

            // Title TextField with testTag and focus handling
            TextField(
                value = titleState.text,
                onValueChange = { newValue ->
                    viewModel.onEvent(AddEditTodoEvent.EnteredTitle(newValue))
                },
                label = { Text(titleState.hint) }, // Display hint as label
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth()
//                    .onFocusChanged { focusState ->
//                        viewModel.onEvent(AddEditTodoEvent.ChangeTitleFocus(focusState))
//                    }
                    .testTag(TestTags.TITLE_TEXT_FIELD) // Applying the test tag
            )

            Spacer(modifier = Modifier.height(16.dp))

//            TextField(
//                value = contentState.text,
//                onValueChange = { newValue ->
//                    viewModel.onEvent(AddEditTodoEvent.EnteredContent(newValue))
//                },
//                label = { Text(contentState.hint) }, // Assuming contentState.hint contains the hint text
//                textStyle = TextStyle(fontSize = 20.sp),
//                //testTag = TestTags.TITLE_TEXT_FIELD,
//                modifier = Modifier
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//                    .fillMaxWidth()
//            )


            TextField(
                value = contentState.text,
                onValueChange = { newValue ->
                    viewModel.onEvent(AddEditTodoEvent.EnteredContent(newValue))
                },
                label = { Text(contentState.hint) }, // Display hint as label
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        viewModel.onEvent(AddEditTodoEvent.ChangeContentFocus(focusState))
                    }
                    .testTag(TestTags.CONTENT_TEXT_FIELD) // Applying the test tag
            )
        }
    }
}
