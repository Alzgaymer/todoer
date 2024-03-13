package com.example.todoer.wear.presentation.todo.day

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.example.todoer.wear.domain.todo.Todo
import com.example.todoer.wear.domain.todo.getFromStartToEndString

@Composable
fun DayTodoes(uiState: DailyTodoesUiState) {
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
fun Todoes(todoes: List<Todo>, contentPadding: PaddingValues = PaddingValues(0.dp)) {
        LazyColumn (
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.End,
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = todoes, key = {it.startDateTime}) {
                Chip(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    colors = ChipDefaults.chipColors(),
                    border = ChipDefaults.chipBorder(),
                    modifier =  Modifier.padding(top = contentPadding.calculateTopPadding(),
                        end = contentPadding.calculateEndPadding(LayoutDirection.Ltr)+ 10.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "${it.payload.substring(0..10)}...",
                            fontSize = TextUnit(14.0f, TextUnitType.Sp),
                            style =  MaterialTheme.typography.body1
                        )
                        Column (
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = AbsoluteAlignment.Left,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = it.getFromStartToEndString(),
                                fontSize = TextUnit(12.0f, TextUnitType.Sp),
                                style =  MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }
        }
}