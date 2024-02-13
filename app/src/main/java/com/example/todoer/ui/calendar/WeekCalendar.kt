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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todoer.TodoerAppTopBar
import com.example.todoer.domain.calendar.compose.WeekCalendar
import com.example.todoer.domain.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.example.todoer.domain.calendar.core.firstDayOfWeekFromLocale
import com.example.todoer.domain.todo.Todo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekCalendarScreen(
    bottomBar: @Composable () -> Unit,
) {
    // create state class
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    var selection by remember { mutableStateOf(currentDate) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )
    val visibleWeek = rememberFirstVisibleWeekAfterScroll(state)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
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
            dayContent = { Day(it.date, isSelected = selection == it.date) { clicked ->
                if (selection != clicked) {
                    selection = clicked
                }
            }},
            weekHeader = { Text(
                // TODO: make this function suspended and in viewmodel
                getWeekPageTitle(visibleWeek),
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