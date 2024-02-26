package com.example.todoer.platform.repositories.todo

import com.example.todoer.domain.todo.Todo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

data class TodoDTO(
    var userID: String? = null,
    var startDate: Timestamp? = null,
    var endDate: Timestamp? = null,
    var remindMeOn: List<Timestamp>? = null,
    var payload: String? = null,
    var location: GeoPoint? = null,
    var done: Boolean? = false
)

fun Timestamp.toLocalDate(): LocalDate =
     this.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

fun Timestamp.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(
        Instant.ofEpochSecond(this.seconds),
        ZoneOffset.UTC
    )



fun TodoDTO.toTodo(): Todo {
    return Todo(
        userID = userID ?: "",
        startDate = startDate ?: throw Exception("failed to parse start date"),
        endDate = endDate?: throw Exception("failed to parse end date"),
        remindMeOn = remindMeOn ?: emptyList(),
        payload = payload ?: "",
        //Location()
        done = done?: false
    )
}
