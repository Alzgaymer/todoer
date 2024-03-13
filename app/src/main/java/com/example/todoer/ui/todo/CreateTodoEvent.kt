package com.example.todoer.ui.todo

import com.google.firebase.Timestamp
import java.time.LocalDate

sealed class CreateTodoEvent {
    data class PayloadChanged(val payload: String): CreateTodoEvent()
    data class StartTimeChanged(val hours: Int, val minutes: Int): CreateTodoEvent()
    data class EndTimeChanged(val hours: Int, val minutes: Int): CreateTodoEvent()
    data class RemindMeOnChanged(val hours: Int, val minutes: Int): CreateTodoEvent()
    data class ReminMeOnDelete(val timestamp: Timestamp):CreateTodoEvent()
    data object Submit : CreateTodoEvent()
    data class LatitudeChanged(val latitude: Double) : CreateTodoEvent()
    data class LongitudeChanged(val longitude: Double) : CreateTodoEvent()
    data class DateChanged(val date: LocalDate) : CreateTodoEvent()
}