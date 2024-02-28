package com.example.todoer.ui.todo

import com.google.firebase.Timestamp

sealed class CreateTodoEvent {
    data class PayloadChanged(val payload: String): CreateTodoEvent()
    data class StartDateChanged(val startDate: Timestamp): CreateTodoEvent()
    data class EndDateChanged(val endDate: Timestamp): CreateTodoEvent()
    data class RemindMeOnChanged(val remindMeOn: List<Timestamp>): CreateTodoEvent()
    data object Submit : CreateTodoEvent()
}