package com.example.todoer.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.todoer.domain.calendar.compose.WeekCalendar
import com.example.todoer.domain.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.example.todoer.domain.todo.Todo
import com.example.todoer.platform.repositories.todo.TodoDTO
import com.example.todoer.platform.repositories.todo.toTodo
import com.example.todoer.ui.TodoerAppTopBar
import com.example.todoer.ui.navigation.TodoerBottomNavigationBar
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

    val todoes by viewModel.todoes.collectAsStateWithLifecycle(listOf(TodoDTO().toTodo()))

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        topBar = { TodoerAppTopBar(navigateUp = {}, canNavigateBack = false, scrollBehavior) },
        bottomBar = { TodoerBottomNavigationBar(navController) },
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
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.padding(start = 15.dp)
            )},
            weekFooter = { Todos(todos = todoes, paddingValues) },
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
            .clickable { onClick(date) }
            ,
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
fun Todos(todos: List<Todo>, contentPadding: PaddingValues) {
    LazyColumn (contentPadding = contentPadding){
        items(items = todos, key = {todo -> todo.startDate}) { todo ->
            Todo(todo)
        }
    }
}

// TODO: make as a sheet
@Composable
fun Todo(todo: Todo) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "User ID: ${todo.userID}", fontSize = 16.sp, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Start Date: ${todo.startDate}", fontSize = 16.sp, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "End Date: ${todo.endDate}", fontSize = 16.sp, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Remind Me On: ${todo.remindMeOn.joinToString(", ")}",
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2, color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Payload: ${todo.payload}", fontSize = 16.sp, color = Color.White)
    }
}