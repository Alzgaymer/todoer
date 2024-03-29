package com.example.todoer.domain.calendar.compose.heatmapcalendar

import com.example.todoer.domain.calendar.compose.HeatMapCalendar

/**
 * Determines the position of the week header
 * composable (Mon, Tue, Wed...) in the [HeatMapCalendar]
 */
enum class HeatMapWeekHeaderPosition {
    /**
     * The header is positioned at the start of the calendar.
     */
    Start,

    /**
     * The header is positioned at the end of the calendar.
     */
    End,
}
