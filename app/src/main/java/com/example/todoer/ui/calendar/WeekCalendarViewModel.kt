package com.example.todoer.ui.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoer.domain.calendar.core.Week
import com.example.todoer.domain.calendar.core.WeekDay
import com.example.todoer.domain.calendar.core.WeekDayPosition
import com.example.todoer.domain.calendar.core.firstDayOfWeekFromLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class WeekCalendarViewModel @Inject constructor(
    //private val
): ViewModel(){

    val currentDate by mutableStateOf( LocalDate.now() )
    val startDate by mutableStateOf( currentDate.minusDays(500) )
    val endDate by mutableStateOf( currentDate.plusDays(500) )
    val firstDayOfWeek by mutableStateOf( firstDayOfWeekFromLocale() )

    var visibleWeek: Week by mutableStateOf(getCurrentWeek())
    var selection by mutableStateOf( currentDate )
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    val weekTitle: Flow<String> =
        snapshotFlow { visibleWeek }
            .mapLatest { getWeekPageTitle(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "",
            )



    public fun chooseDay(clicked: LocalDate) {
        if (selection != clicked) {
            selection = clicked
        }
    }

    private fun getCurrentWeek(): Week {
        val list = mutableListOf<WeekDay>()
        (0..6).forEach{ index ->
            list.add(WeekDay(
                date = LocalDate.now().plusDays(index.toLong()),
                position = WeekDayPosition.InDate
            ))
        }

        return Week(list)
    }
}