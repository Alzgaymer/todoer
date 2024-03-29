package com.example.todoer.ui.todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todoer.R
import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.example.todoer.ui.TodoerAppTopBar
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodoScreen(
    uiState: CreateTodoFormState,
    onMapNavigate: (LatLng) -> Unit,
    onBackButton: () -> Unit,
    onPayloadChange: (String) -> Unit,
    onStartTimeChange: (Int, Int) -> Unit,
    onEndTimeChange: (Int, Int) -> Unit,
    onSubmitEvent: () -> Unit,
    onRemindmeOnValueChange: (Int, Int) -> Unit,
    onRemindmeOnDelete: (Timestamp) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            TodoerAppTopBar(
                canNavigateBack = true,
                navigateUp = onBackButton,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSubmitEvent) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = null)
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { contentPadding ->
        TodoForm(
            state = uiState,
            onMapNavigate = onMapNavigate,
            payloadValueChange = onPayloadChange,
            startDateValueChange = onStartTimeChange,
            endDateValueChange = onEndTimeChange,
            remindMeOnValueChange = onRemindmeOnValueChange,
            remindMeOnDeleteValue = onRemindmeOnDelete,
            contentPadding = contentPadding
        )
    }
}


val roundedCornerShape = RoundedCornerShape(10.dp)

@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun TodoForm(
    state: CreateTodoFormState,
    onMapNavigate: (LatLng) -> Unit,
    payloadValueChange: (String) -> Unit,
    startDateValueChange: (Int,Int) -> Unit,
    endDateValueChange: (Int,Int) -> Unit,
    remindMeOnValueChange: (Int,Int) -> Unit,
    remindMeOnDeleteValue: (Timestamp) -> Unit,
    contentPadding: PaddingValues
) {
    Column {
        // Payload
        OutlinedTextField(
            value = state.payload,
            onValueChange = payloadValueChange,
            isError = state.payloadError != null,
            label = { Text(text = "Todo") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email),
            supportingText = {OnFieldError(error = state.payloadError)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = contentPadding.calculateTopPadding() + 10.dp,
                    start = 10.dp,
                    end = 10.dp
                ),
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
            )
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                horizontalAlignment = AbsoluteAlignment.Right
            ) {
                TodoTimePickerField(
                    text = "Start time",
                    timestamp = state.startDate,
                    error = state.startDateError,
                    onTimeChanged = startDateValueChange
                )
            }
            Column(
                horizontalAlignment = AbsoluteAlignment.Left
            ) {
                TodoTimePickerField(
                    text = "End time",
                    timestamp = state.endDate,
                    error = state.endDateError,
                    onTimeChanged = endDateValueChange
                )
            }
        }

        var remindMeOnVisible by remember { mutableStateOf(true)}
        Row {
            Text(
                text = stringResource(id = R.string.remind_me_on),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp),
            )
            IconButton(onClick = { remindMeOnVisible = !remindMeOnVisible }) {
                if (remindMeOnVisible)
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
                else
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }
        }

        AnimatedVisibility(
            visible = remindMeOnVisible,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            OutlinedCard(
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 10.dp
                ),
                shape = roundedCornerShape,
                modifier = Modifier.padding(16.dp),
            ) {
                FlowColumn(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()

                ) {
                    for (time in state.remindMeOn
                        .sortedWith(
                            compareBy({ it.toLocalDateTime().hour },
                                { it.toLocalDateTime().minute })
                        )
                    ) {
                        Box(
                            Modifier.padding(3.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = time.toLocalDateTime(dateTimeFormatter),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(10.dp)
                                )

                                Row {
                                    // edit
                                    var reminMeOnEditTimeVisible by remember { mutableStateOf(false) }
                                    IconButton(
                                        onClick = { reminMeOnEditTimeVisible = true },
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            Icons.Outlined.Edit,
                                            stringResource(id = R.string.icon_edit)
                                        )
                                    }

                                    TodoTimePicker(
                                        visible = reminMeOnEditTimeVisible,
                                        onDismiss = { reminMeOnEditTimeVisible = false },
                                        onConfirm = { h, m ->
                                            remindMeOnDeleteValue(time)
                                            remindMeOnValueChange(h, m)
                                            reminMeOnEditTimeVisible = false
                                        }
                                    )

                                    // delete
                                    IconButton(
                                        onClick = { remindMeOnDeleteValue(time) },
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            Icons.Outlined.Clear,
                                            stringResource(id = R.string.icon_clear)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    OnFieldError(
                        state.remindMeOnError,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                var remindMeOnTimePickerVisible by remember {mutableStateOf(false)}
                ElevatedButton(
                    onClick = {remindMeOnTimePickerVisible = true},
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp,
                            bottom = 10.dp,
                            start = 100.dp,
                            end = 100.dp
                        )
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add_button)
                    )

                }
                TodoTimePicker(
                    visible = remindMeOnTimePickerVisible,
                    onDismiss = { remindMeOnTimePickerVisible = false},
                ){ hour, minutes ->
                    remindMeOnValueChange(hour, minutes)
                    remindMeOnTimePickerVisible = false
                }
            }
        }
        //Location
        TextField(
            value = "${state.location.latitude}, ${state.location.longitude}",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { LocationIconButton{
                onMapNavigate(state.location)
            } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

    }
}

@Composable
fun LocationIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null
        )
    }
}

@Composable
private fun TodoTimePickerField(
    text: String,
    timestamp: Timestamp,
    error: String?,
    onTimeChanged: (Int, Int) -> Unit
) {

    var visibleTimePicker by remember { mutableStateOf(false) }
    TextField(
        value = timestamp.toLocalDateTime(dateTimeFormatter),
        onValueChange = {},
        label = { Text(text) },
        readOnly = true,
        trailingIcon = { EditIconButton {
            visibleTimePicker = true
        }},
        isError = error != null,
        supportingText = {OnFieldError(error = error)},
        modifier = Modifier
            .width(200.dp)
            .padding(
                start = 10.dp,
                end = 10.dp
            ),
    )

    TodoTimePicker(
        visible = visibleTimePicker,
        onDismiss = { visibleTimePicker = false },
        onConfirm = { hours, minutes ->
            visibleTimePicker = false
            onTimeChanged(hours, minutes)
        }
    )
}

@Composable
fun EditIconButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTimePicker(
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    if (visible){
        val now = LocalDateTime.now().atOffset(ZoneOffset.UTC)
        val timePickerState = rememberTimePickerState(
            initialHour = now.hour,
            initialMinute = now.minute,
            is24Hour = true
        )

        DatePickerDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                ElevatedButton(
                    onClick = {
                        coroutineScope.launch {
                            onConfirm(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                        }
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