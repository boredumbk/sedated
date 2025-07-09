package com.example.sedatedjuly.feature_todo.presentation.add_edit_todo.components

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
import kotlinx.coroutines.launch

@Composable
fun AddEditTaskScreen (
    navController: NavController,
    taskColor: Int,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val titleState = viewModel.taskTitle.value
    val contentState = viewModel.taskContent.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val taskBackgroundAnimatable = remember {
        Animatable(
            Color(if(taskColor != -1) taskColor else viewModel.taskColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditTaskViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditTaskViewModel.UiEvent.SaveTask -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
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
                .background(taskBackgroundAnimatable.value)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Task.taskColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.taskColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    taskBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditTaskEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

//            TextField(
//                value = titleState.text,
//                onValueChange = { newValue ->
//                    viewModel.onEvent(AddEditTaskEvent.EnteredTitle(newValue))
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
                    viewModel.onEvent(AddEditTaskEvent.EnteredTitle(newValue))
                },
                label = { Text(titleState.hint) }, // Display hint as label
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth()
//                    .onFocusChanged { focusState ->
//                        viewModel.onEvent(AddEditTaskEvent.ChangeTitleFocus(focusState))
//                    }
                    .testTag(TestTags.TITLE_TEXT_FIELD) // Applying the test tag
            )

            Spacer(modifier = Modifier.height(16.dp))

//            TextField(
//                value = contentState.text,
//                onValueChange = { newValue ->
//                    viewModel.onEvent(AddEditTaskEvent.EnteredContent(newValue))
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
                    viewModel.onEvent(AddEditTaskEvent.EnteredContent(newValue))
                },
                label = { Text(contentState.hint) }, // Display hint as label
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        viewModel.onEvent(AddEditTaskEvent.ChangeContentFocus(focusState))
                    }
                    .testTag(TestTags.CONTENT_TEXT_FIELD) // Applying the test tag
            )
        }
    }
}
