package com.example.todoer.ui.todo

sealed class CreateTodoEvent {
    data class PayloadChanged(val payload: String): CreateTodoEvent()
    data class StartTimeChanged(val hours: Int, val minutes: Int): CreateTodoEvent()
    data class EndTimeChanged(val hours: Int, val minutes: Int): CreateTodoEvent()
    data class RemindMeOnChanged(val hours: Int, val minutes: Int): CreateTodoEvent()
    data object Submit : CreateTodoEvent()
}