package com.example.todoer.domain.todo

import java.time.LocalDate

data class Todo (
    var userID: String,
    var startDate: LocalDate ,
    var endDate: LocalDate,
    var remindMeOn: List<LocalDate> ,
    var payload: String ,
   // var location: Location?,
)