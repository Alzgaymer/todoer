package com.example.todoer.ui.todo

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.example.todoer.ui.TodoerAppTopBar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodoScreen(
    selectedDate: LocalDate,
    viewModel: CreateTodoViewModel = hiltViewModel(),
    onBackButton: () -> Unit
) {
    val state = viewModel.state.copy(selectedDate = selectedDate)
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect{ event ->
            when (event) {
                is CreateTodoViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context,"Todo created!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = { TodoerAppTopBar(
            canNavigateBack = true,
            navigateUp = onBackButton,
            scrollBehavior = scrollBehavior
        )}
    ) { contentPadding ->
        TodoForm(
            state = state,
            payloadValueChange = viewModel::payloadChange,
            startDateValueChange = viewModel::startTimeChange,
            endDateValueChange = viewModel::endTimeChange,
            remindMeOnValueChange = {_,_ ->},
            contentPadding = contentPadding
        )
    }
}

private val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

@Composable
fun OnFieldError(error: String?, modifier: Modifier = Modifier) {
    error?.let { error ->
        Text(
            text = error,
            color = MaterialTheme.colorScheme.onError,
            modifier = modifier
        )
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun TodoForm(
    state: CreateTodoState,
    payloadValueChange: (String) -> Unit,
    startDateValueChange: (Int,Int) -> Unit,
    endDateValueChange: (Int,Int) -> Unit,
    remindMeOnValueChange: (Int,Int) -> Unit,
    contentPadding: PaddingValues
) {
    Column {
        // Payload
        OutlinedTextField(
            value = state.payload,
            onValueChange = payloadValueChange,
            isError = state.payloadError != null,
            placeholder = { Text(text = "Todo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = contentPadding.calculateTopPadding() + 10.dp,
                    start = 10.dp,
                    end = 10.dp
                ),
        )
        OnFieldError(error = state.payloadError)

        Row {
            // Time picker for start date
            var startTimePickerState by remember {mutableStateOf(TodoTimePickerState())}
            OutlinedTextField(
                value = dateTimeFormatter.format(state.startDate.toLocalDateTime()) ,
                onValueChange = {},
                label = { Text("Start Time") },
                readOnly = true,
                modifier = Modifier
                    .width(150.dp)
                    .padding(
                        start = 10.dp,
                        end = 10.dp
                    )
                    .clickable {
                        startTimePickerState = startTimePickerState.copy(visible = true)
                    }
            )
            OnFieldError(error = state.startDateError,
                modifier = Modifier.align(Alignment.Bottom)
            )


            TimePicker(startTimePickerState) { hours, minutes ->
                startTimePickerState = startTimePickerState.copy(visible = false)
                startDateValueChange(hours, minutes)
            }

            // Time picker for end date
            val endDatePickerState by remember { mutableStateOf(TodoTimePickerState()) }
            OutlinedTextField(
                value = dateTimeFormatter.format(state.endDate.toLocalDateTime()),
                onValueChange = {},
                label = { Text("End Time") },
                readOnly = true,
                modifier = Modifier
                    .width(150.dp)
                    .padding(
                        start = 10.dp,
                        end = 10.dp
                    )
                    .clickable {
                        endDatePickerState.visible = true
                    }
            )
            OnFieldError(error = state.endDateError,
                modifier = Modifier.align(Alignment.Bottom)
            )
            TimePicker(endDatePickerState) { hours, minutes ->
                endDatePickerState.visible = false
                endDateValueChange(hours, minutes)
            }
        }

        Row {// Time picker for remind me on
            val remindMeOnPickerState by remember { mutableStateOf(TodoTimePickerState()) }
            OutlinedTextField(
                value = state.remindMeOn.joinToString(", ") { it.toString() },
                onValueChange = {},
                label = { Text("Remind Me On (Comma-separated)") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = contentPadding.calculateTopPadding(),
                        start = 10.dp,
                        end = 10.dp
                    )
                    .clickable {
                        remindMeOnPickerState.visible = true
                    }
            )
            OnFieldError(
                error = state.remindMeOnError,
                modifier = Modifier.align(Alignment.Bottom)
            )
            TimePicker(remindMeOnPickerState) { hours, minutes ->
                remindMeOnPickerState.visible = false
                remindMeOnValueChange(hours, minutes)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    state: TodoTimePickerState,
    onTimeSelected: (Int, Int) -> Unit
) {
    if (state.visible) {
        val now = LocalDateTime.now()
        val timePickerState = rememberTimePickerState(
            initialHour = now.hour,
            initialMinute = now.minute,
            is24Hour = true
        )

        DatePickerDialog(
            onDismissRequest = {state.visible = false},
            dismissButton = {
                TextButton(
                    onClick = {state.visible = false}
                ) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        state.visible = false
                        onTimeSelected(
                            timePickerState.hour,
                            timePickerState.minute
                        )

                    },
                ) {
                    Text("OK")
                }
            },
        ) {
            TimePicker(timePickerState)
        }
    }
}
