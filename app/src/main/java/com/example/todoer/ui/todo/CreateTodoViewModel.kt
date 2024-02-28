package com.example.todoer.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoer.domain.todo.TodoesRepository
import com.example.todoer.domain.validation.ValidateEndDate
import com.example.todoer.domain.validation.ValidatePayload
import com.example.todoer.domain.validation.ValidateRemindMeOn
import com.example.todoer.domain.validation.ValidateStartDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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

    fun onEvent(event: CreateTodoEvent) {
        when(event){
            is CreateTodoEvent.EndDateChanged -> state = state.copy(endDate = event.endDate)
            is CreateTodoEvent.PayloadChanged -> state = state.copy(payload = event.payload)
            is CreateTodoEvent.RemindMeOnChanged -> state = state.copy(remindMeOn = event.remindMeOn)
            is CreateTodoEvent.StartDateChanged -> state = state.copy(startDate = event.startDate)
            is CreateTodoEvent.Submit -> submit()
        }
    }

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

        if (hasError){
            state = state.copy(
                payloadError = payloadResult.error,
                startDateError = startDateResult.error,
                endDateError = endDateResult.error,
                remindMeOnError = remindMeOnResult.error
            )
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }

    }

    fun payloadChange(text: String) {
        onEvent(CreateTodoEvent.PayloadChanged(text))
    }


    sealed class ValidationEvent {
        data object Success: ValidationEvent()
    }
}