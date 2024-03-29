package com.example.todoer.domain.calendar.compose

import com.example.todoer.domain.calendar.core.OutDateStyle
import java.time.DayOfWeek

internal data class CalendarInfo(
    val indexCount: Int,
    private val firstDayOfWeek: DayOfWeek? = null,
    private val outDateStyle: OutDateStyle? = null,
)
