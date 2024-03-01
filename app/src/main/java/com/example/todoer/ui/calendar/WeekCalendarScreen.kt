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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.todoer.R
import com.example.todoer.domain.calendar.compose.WeekCalendar
import com.example.todoer.domain.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.getFromStartToEndString
import com.example.todoer.domain.todo.remindMeOn
import com.example.todoer.platform.repositories.todo.toLocalDate
import com.example.todoer.ui.TodoerAppTopBar
import com.example.todoer.ui.navigation.TodoerBottomNavigationBar
import com.example.todoer.ui.navigation.navigateToCreateToDo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekCalendarScreen(
    navController: NavHostController,
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

    val todoes by viewModel.todoes.collectAsStateWithLifecycle(emptyList())

    Scaffold(
        floatingActionButton = { CalendarFloatingActionButton(onAddClick = {
                navController.navigateToCreateToDo(viewModel.selection)
        }) },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = { TodoerAppTopBar(
            navigateUp = {},
            canNavigateBack = false,
            scrollBehavior
        ) },
        bottomBar = { TodoerBottomNavigationBar(navController) },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->

        WeekCalendar(
            state = state,
            dayContent = { Day(
                date = it.date,
                isSelected = viewModel.selection == it.date,
                onClick = viewModel::chooseDay
            ) },
            weekHeader = { Text(
                text = weekTitle,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 15.dp)
            )},
            weekFooter = { Todos(todos = todoes, viewModel.selection) },
            contentPadding = paddingValues,
        )
    }
}


@Composable
fun CalendarFloatingActionButton(onAddClick: () -> Unit) {
    FloatingActionButton(
        onClick = onAddClick,
        shape = RoundedCornerShape(20.dp),
    ) {
        Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.fab_add))
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
                color = selection(isSelected),
                fontWeight = FontWeight.Light,
            )
            Text(
                text = dateFormatter.format(date),
                style = MaterialTheme.typography.labelMedium,
                color = selection(isSelected),
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
fun selection(isSelected: Boolean) = when (isSelected) {
    true -> MaterialTheme.colorScheme.primary
    false-> MaterialTheme.colorScheme.onBackground
}

@Composable
fun Todos(todos: List<Todo>, selectedDay: LocalDate) {
    val filtered = todos.filter {
        it.startDateTime.toLocalDate().atStartOfDay() == selectedDay.atStartOfDay()
    }
    LazyColumn{
        items(items = filtered, key = {todo -> todo.startDateTime}) { todo ->
            Todo(todo)
        }
    }
}

@Composable
fun Todo(todo: Todo) {
    Card(
        onClick = {

        },
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            contentColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        elevation = CardDefaults.elevatedCardElevation(),
        enabled = todo.done,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = todo.payload,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = todo.getFromStartToEndString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Remind Me On: ${todo.remindMeOn()}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Done: ${if (todo.done) "Yes" else "No"}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}