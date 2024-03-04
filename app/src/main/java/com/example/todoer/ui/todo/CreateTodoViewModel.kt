package com.example.todoer.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.TodoesRepository
import com.example.todoer.domain.validation.ValidateEndDate
import com.example.todoer.domain.validation.ValidatePayload
import com.example.todoer.domain.validation.ValidateRemindMeOn
import com.example.todoer.domain.validation.ValidateStartDate
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CreateTodoViewModel @Inject constructor(
    @Named("userID") private val userID: String?,
    private val todoesRepository: TodoesRepository,
    private val validatePayload: ValidatePayload = ValidatePayload(),
    private val validateStartDate: ValidateStartDate = ValidateStartDate(),
    private val validateEndDate: ValidateEndDate = ValidateEndDate(),
    private val validateRemindMeOn: ValidateRemindMeOn = ValidateRemindMeOn(),
) : ViewModel() {

    var state by mutableStateOf(CreateTodoState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private fun mapTimestamp(hours: Int, minutes: Int): Timestamp {
        val calendar = Calendar.getInstance()

        calendar.set(
            /* year = */ state.selectedDate.year,
            /* month = */ state.selectedDate.monthValue-1, // -1 because month value start from 0 not 1
            /* date = */ state.selectedDate.dayOfMonth,
            /* hourOfDay = */ hours,
            /* minute = */ minutes)
        calendar.timeZone = TimeZone.getTimeZone("UTC")

        return Timestamp(calendar.time)
    }

    fun onEvent(event: CreateTodoEvent) {
        when (event) {
            is CreateTodoEvent.EndTimeChanged -> {
                state = state.copy(
                    endDate = mapTimestamp(event.hours, event.minutes)
                )
            }

            is CreateTodoEvent.PayloadChanged -> state = state.copy(payload = event.payload)
            is CreateTodoEvent.RemindMeOnChanged -> {
                state = state.copy(
                    remindMeOn = state.remindMeOn +mapTimestamp(event.hours, event.minutes))
            }

            is CreateTodoEvent.StartTimeChanged -> {
                state = state.copy(
                    startDate = mapTimestamp(event.hours, event.minutes)
                )
            }

            is CreateTodoEvent.Submit -> submit()
            is CreateTodoEvent.ReminMeOnDelete -> {
                state = state.copy(remindMeOn = state.remindMeOn - event.timestamp)
            }
        }
    }

    // TODO: create function to delete reminds me on
    // FIXME: validate remind me on dates
    private fun submit() {
        val payloadResult = validatePayload.validate(state.payload)
        val startDateResult = validateStartDate.validate(state.startDate, state.endDate)
        val endDateResult = validateEndDate.validate(state.startDate, state.endDate)
        val remindMeOnResult = validateRemindMeOn.validate(state.remindMeOn, state.startDate)

        val hasError = listOf(
            payloadResult,
            startDateResult,
            endDateResult,
            remindMeOnResult
        ).any { !it.successful }

        when(hasError) {
             true -> {
                state = state.copy(
                    payloadError = payloadResult.error,
                    startDateError = startDateResult.error,
                    endDateError = endDateResult.error,
                    remindMeOnError = remindMeOnResult.error
                )
            }
            false -> {
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Success)
                    todoesRepository.setTodo(userID ?: "", Todo(
                        userID = userID ?: "",
                        startDateTime = state.startDate,
                        endDateTime = state.endDate,
                        remindMeOn = state.remindMeOn,
                        payload = state.payload,
                        done = false
                    )
                    )
                }
            }
        }


    }

    fun payloadChange(text: String) {
        onEvent(CreateTodoEvent.PayloadChanged(text))
    }

    fun startTimeChange(hours: Int, minutes: Int) {
        onEvent(CreateTodoEvent.StartTimeChanged(hours, minutes))
    }

    fun endTimeChange(hours: Int, minutes: Int) {
        onEvent(CreateTodoEvent.EndTimeChanged(hours, minutes))
    }

    fun submitEvent() {
        onEvent(CreateTodoEvent.Submit)
    }

    fun remindmeOnValueChange(hour: Int, minutes: Int) {
        onEvent(CreateTodoEvent.RemindMeOnChanged(hour, minutes))
    }

    fun remindmeOnDelete(timestamp: Timestamp) {
        onEvent(CreateTodoEvent.ReminMeOnDelete(timestamp))
    }

    sealed class ValidationEvent {
        data object Success: ValidationEvent()
    }
}