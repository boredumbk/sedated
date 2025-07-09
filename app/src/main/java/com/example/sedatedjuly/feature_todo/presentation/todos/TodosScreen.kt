package com.example.sedatedjuly.feature_todo.presentation.todos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TodosScreen(
    navController: NavController,
    viewModel: TodosViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditTaskScreen.route)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },


        ) { paddingValues ->
        // Apply padding provided by the Scaffold to the Column
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Flow with your todos",
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(TasksEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag(TestTags.ORDER_SECTION),
                    taskOrder = state.taskOrder,
                    onOrderChange = {
                        viewModel.onEvent(TasksEvent.Order(it))
                    }
                )
            }


            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.tasks) { task ->
                    TaskItem(
                        task = task,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color(task.color))
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditTaskScreen.route +
                                            "?taskId=${task.id}&taskColor=${task.color}"
                                )
                            },

                        onDeleteClick = {
                            viewModel.onEvent(TasksEvent.DeleteTask(task))
                            scope.launch {
                                // Show the snackbar and capture the result
                                val result = snackbarHostState.showSnackbar(
                                    message = "Task deleted",
                                    actionLabel = "Undo"
                                )

                                // Handle the action performed on the snackbar
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(TasksEvent.RestoreTask)

                                }
                            }

                            // Launch another coroutine for delay and dismissal
                            scope.launch {
                                // Delay for 5 seconds
                                delay(10000)

                                // Dismiss the snackbar after the delay
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}