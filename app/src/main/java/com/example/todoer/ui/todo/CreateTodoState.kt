package com.example.todoer.ui.todo

import com.google.firebase.Timestamp

data class CreateTodoState(
    val payload: String = "",
    val payloadError: String? = null,
    val startDate: Timestamp = Timestamp.now(),
    val startDateError: String? = null,
    val endDate: Timestamp = Timestamp.now(),
    val endDateError: String? = null,
    val remindMeOn: List<Timestamp> = emptyList(),
    val remindMeOnError: String? = null,
)
