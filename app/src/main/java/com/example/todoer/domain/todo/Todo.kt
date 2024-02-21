package com.example.todoer.domain.todo

import android.location.Location
import java.time.LocalDate

data class Todo (
    private val userID: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val remindMeOn: List<LocalDate>,
    val payload: String,
    val location: Location
){

}