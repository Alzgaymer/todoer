package com.example.todoer.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoer.domain.calendar.compose.WeekCalendar
import com.example.todoer.domain.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.example.todoer.domain.todo.Todo
import com.example.todoer.ui.TodoerAppTopBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekCalendarScreen(
    bottomBar: @Composable () -> Unit,
    viewModel: WeekCalendarViewModel = hiltViewModel()
) {

    val state = rememberWeekCalendarState(
        startDate = viewModel.startDate,
        endDate = viewModel.endDate,
        firstVisibleWeekDate = viewModel.currentDate,
        firstDayOfWeek = viewModel.firstDayOfWeek
    )

    viewModel.visibleWeek = rememberFirstVisibleWeekAfterScroll(state)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val weekTitle = viewModel.weekTitle
        .collectAsStateWithLifecycle("")
        .value

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        topBar = { TodoerAppTopBar(navigateUp = {}, canNavigateBack = false, scrollBehavior) },
        bottomBar = bottomBar,
    ) { paddingValues ->

        WeekCalendar(
            state = state,
            dayContent = { Day(
                date = it.date,
                isSelected = viewModel.selection == it.date,
                onClick = viewModel::chooseDay
            ) },
            weekHeader = { Text(
                // TODO: make this function suspendable and in viewmodel
                text = weekTitle,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.padding(start = 15.dp)
            )},
            weekFooter = {// TODO: make viewmodel getTodos
                 },
            contentPadding = paddingValues,
        )
    }
}
private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
private fun Day(date: LocalDate, isSelected: Boolean, onClick: (LocalDate) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = date.dayOfWeek.displayText(),
                style = MaterialTheme.typography.labelLarge,
                color = ifSelected(isSelected),
                fontWeight = FontWeight.Light,
            )
            Text(
                text = dateFormatter.format(date),
                style = MaterialTheme.typography.labelMedium,
                color = ifSelected(isSelected),
                fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
fun ifSelected(isSelected: Boolean) = when {
    isSelected -> MaterialTheme.colorScheme.primaryContainer
    else -> MaterialTheme.colorScheme.surfaceVariant
}

@Composable
fun Todos(todos: List<Todo>) {

}

// TODO: make as a sheet
@Composable
fun Todo(todo: Todo) {

}