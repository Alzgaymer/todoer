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
import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.TodosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class WeekCalendarViewModel @Inject constructor(
    @Named("userID") private val userID: String?,
    private val todosRepository: TodosRepository
): ViewModel(){

    val currentDate by mutableStateOf( LocalDate.now() )
    val startDate by mutableStateOf( currentDate.minusDays(500) )
    val endDate by mutableStateOf( currentDate.plusDays(500) )
    val firstDayOfWeek by mutableStateOf( firstDayOfWeekFromLocale() )

    var visibleWeek: Week by mutableStateOf(getCurrentWeek())
    var selection by mutableStateOf( currentDate )
        private set


    @OptIn(ExperimentalCoroutinesApi::class)
    var todoes: Flow<List<Todo>> =
        snapshotFlow { selection }
            .flowOn(Dispatchers.IO)
            .mapLatest { todosRepository.getTodoes(userID?: "", it) }
            .flowOn(Dispatchers.Main)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList(),
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val weekTitle: Flow<String> =
        snapshotFlow { visibleWeek }
            .flowOn(Dispatchers.IO)
            .mapLatest { getWeekPageTitle(it) }
            .flowOn(Dispatchers.Main)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "",
            )

    fun chooseDay(clicked: LocalDate) {
        if (selection != clicked) {
            selection = clicked
        }
    }

    private fun getCurrentWeek(): Week {
        val list = mutableListOf<WeekDay>()
        (0..6).forEach { index ->
            list.add(
                WeekDay(
                    date = LocalDate.now().plusDays(index.toLong()),
                    position = WeekDayPosition.InDate
                )
            )
        }

        return Week(list)
    }
}