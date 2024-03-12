package com.example.todoer.wear.presentation.todo.day

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.example.todoer.wear.domain.todo.Todo

@Composable
fun DayTodoes(uiState: DailyTodoesUiState) {
    val scrollState = ScrollState(0)

    Scaffold() {
        when (uiState.list.isEmpty()) {
            true -> {EmptyScreen()}
            else -> {Todoes(todoes = uiState.list)}
        }
    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
fun PreviewDayTodoes() {
    DayTodoes(DailyTodoesUiState())
}

@Composable
fun EmptyScreen() {
    Row (verticalAlignment = Alignment.CenterVertically) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "No todoes for today",
                modifier = Modifier.padding( top =  30.dp, start =  20.dp))
      }
    }
}

@Composable
fun Todoes(todoes: List<Todo>) {
    Row (verticalAlignment = Alignment.CenterVertically) {
       Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "There are some todoes for today",
                modifier = Modifier.padding(top =  30.dp, start =  20.dp))
       }
    }
}