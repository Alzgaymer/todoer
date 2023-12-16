package com.example.todoer.ui.task

import java.time.LocalDate

data class TaskState (
    val day: LocalDate = LocalDate.now()
)