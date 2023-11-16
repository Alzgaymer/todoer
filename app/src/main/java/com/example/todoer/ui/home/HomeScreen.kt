package com.example.todoer.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun HomeScreen() {

    val startDay = LocalDate.now()
    val endDay = startDay.plusDays(7)


    val weekCalendarState = rememberWeekCalendarState(
        startDate = startDay,
        endDate = endDay,
        firstDayOfWeek = DayOfWeek.MONDAY,
    )

    WeekCalendar (
        state = weekCalendarState,
        dayContent = { day -> Text(text = day.date.dayOfWeek.name) },
        modifier = Modifier,
    )
}