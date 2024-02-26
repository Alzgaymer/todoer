package com.example.todoer.domain.todo

import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.google.firebase.Timestamp
import java.time.format.DateTimeFormatter


data class Todo (
    var userID: String,
    var startDate: Timestamp,
    var endDate: Timestamp,
    var remindMeOn: List<Timestamp>,
    var payload: String,
   // var location: Location?,
    var done: Boolean
)

fun Todo.toHashMap(): HashMap<String, Any> = hashMapOf(
    "userID" to userID,
    "startDate" to startDate,
    "endDate" to endDate,
    "remindMeOn" to remindMeOn,
    "payload" to payload,
    "done" to done,
)


private val hourFormatter = DateTimeFormatter.ofPattern("hh:mm")


fun Todo.getFromStartToEndString(): String {
    val stringBuilder = StringBuilder()

    stringBuilder.append(startDate
        .toLocalDateTime().toLocalTime().format(hourFormatter))

    stringBuilder.append(" - ")

    stringBuilder.append(endDate
        .toLocalDateTime().toLocalTime().format(hourFormatter))

    return stringBuilder.toString()
}

fun Todo.remindMeOn(): String = remindMeOn.joinToString {
        it.toLocalDateTime().toLocalTime().format(hourFormatter)
    }
