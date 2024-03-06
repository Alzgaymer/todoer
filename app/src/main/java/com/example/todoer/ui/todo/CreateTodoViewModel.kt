package com.example.todoer.ui.todo

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.TodoesRepository
import com.example.todoer.domain.validation.ValidateEndDate
import com.example.todoer.domain.validation.ValidatePayload
import com.example.todoer.domain.validation.ValidateRemindMeOn
import com.example.todoer.domain.validation.ValidateStartDate
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CreateTodoViewModel @Inject constructor(
    @Named("userID") private val userID: String?,
    private val todoesRepository: TodoesRepository,
    private val savedStateHandle: SavedStateHandle,
    private val validatePayload: ValidatePayload = ValidatePayload(),
    private val validateStartDate: ValidateStartDate = ValidateStartDate(),
    private val validateEndDate: ValidateEndDate = ValidateEndDate(),
    private val validateRemindMeOn: ValidateRemindMeOn = ValidateRemindMeOn(),
) : ViewModel() {

    private var _state = MutableStateFlow(CreateTodoFormState())
    val state: StateFlow<CreateTodoFormState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private fun mapTimestamp(hours: Int, minutes: Int): Timestamp {
        val calendar = Calendar.getInstance()

        calendar.set(
            /* year = */ _state.value.selectedDate.year,
            /* month = */ _state.value.selectedDate.monthValue-1, // -1 because month value start from 0 not 1
            /* date = */ _state.value.selectedDate.dayOfMonth,
            /* hourOfDay = */ hours,
            /* minute = */ minutes)
        calendar.timeZone = TimeZone.getTimeZone("UTC")

        return Timestamp(calendar.time)
    }

    fun onEvent(event: CreateTodoEvent) {
        when (event) {
            is CreateTodoEvent.EndTimeChanged -> {
                _state.value =
                    _state.value.copy(
                    endDate = mapTimestamp(event.hours, event.minutes)
                )
            }

            is CreateTodoEvent.PayloadChanged ->
                _state.value =
                    _state.value.copy(payload = event.payload)
            is CreateTodoEvent.RemindMeOnChanged -> {
                _state.value =
                    _state.value.copy(
                    remindMeOn = _state.value.remindMeOn +mapTimestamp(event.hours, event.minutes))
            }

            is CreateTodoEvent.StartTimeChanged -> {
                _state.value =
                    _state.value.copy(
                    startDate = mapTimestamp(event.hours, event.minutes)
                )
            }
            is CreateTodoEvent.Submit -> submit()
            is CreateTodoEvent.ReminMeOnDelete -> {
                _state.value =
                    _state.value.copy(remindMeOn = _state.value.remindMeOn - event.timestamp)
            }
            is CreateTodoEvent.DateChanged -> {
                _state.value =
                    _state.value.copy(selectedDate = event.date) }

            is CreateTodoEvent.LatitudeChanged ->
                _state.value =
                    _state.value.copy(
                        location = LatLng(event.latitude, _state.value.location.longitude))
            is CreateTodoEvent.LongitudeChanged ->
                _state.value =
                    _state.value.copy(
                        location = LatLng(_state.value.location.latitude,event.longitude))
        }
    }

    // TODO: create function to delete reminds me on
    // FIXME: validate remind me on dates
    private fun submit() {
        val payloadResult = validatePayload.validate(_state.value.payload)
        val startDateResult = validateStartDate.validate(_state.value.startDate, _state.value.endDate)
        val endDateResult = validateEndDate.validate(_state.value.startDate, _state.value.endDate)
        val remindMeOnResult = validateRemindMeOn.validate(_state.value.remindMeOn, _state.value.startDate)

        val hasError = listOf(
            payloadResult,
            startDateResult,
            endDateResult,
            remindMeOnResult
        ).any { !it.successful }

        when(hasError) {
             true -> {
                _state.value = _state.value.copy(
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
                        startDateTime = _state.value.startDate,
                        endDateTime = _state.value.endDate,
                        remindMeOn = _state.value.remindMeOn,
                        payload = _state.value.payload,
                        location = _state.value.location,
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

    fun latitudeChanged(latitude: Double){
        onEvent(CreateTodoEvent.LatitudeChanged(latitude))
    }

    fun longitudeChanged(longitude: Double){
        Log.d("longitude", longitude.toString())
        onEvent(CreateTodoEvent.LongitudeChanged(longitude))
    }

    fun dateChanged(date: LocalDate) {
        onEvent(CreateTodoEvent.DateChanged(date))
    }

    sealed class ValidationEvent {
        data object Success: ValidationEvent()
    }
}