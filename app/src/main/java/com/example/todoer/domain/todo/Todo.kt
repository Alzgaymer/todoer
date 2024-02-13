package com.example.todoer.domain.todo

import java.time.LocalDate

class Todo (
    val startDate: LocalDate,
    val endDate: LocalDate,
    val remindMeOn: List<LocalDate>,
    val repeat: LocalDate
){

}