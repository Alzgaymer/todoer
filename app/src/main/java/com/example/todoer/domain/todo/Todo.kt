package com.example.todoer.domain.todo

import android.location.Location
import java.time.LocalDate
import java.util.UUID

data class Todo (
    private val userID: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val remindMeOn: List<LocalDate>,
    val payload: String,
    val location: Location
){

}