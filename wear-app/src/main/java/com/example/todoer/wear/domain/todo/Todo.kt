package com.example.todoer.wear.domain.todo

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class Todo (
    var userID: String,
    var startDateTime: Timestamp,
    var endDateTime: Timestamp,
    var remindMeOn: List<Timestamp>,
    var payload: String,
    var location: LatLng,
    var done: Boolean
)

private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun Todo.getFromStartToEndString(): String {
    val stringBuilder = StringBuilder()

    stringBuilder.append(startDateTime
        .toLocalDateTime().toLocalTime().format(hourFormatter))

    stringBuilder.append(" - ")

    stringBuilder.append(endDateTime
        .toLocalDateTime().toLocalTime().format(hourFormatter))

    return stringBuilder.toString()
}

fun Timestamp.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(
        Instant.ofEpochSecond(this.seconds),
        ZoneOffset.UTC
    )

fun Timestamp.toLocalDateTime(formatter: DateTimeFormatter): String =
    formatter.format(
        LocalDateTime.ofInstant(
        Instant.ofEpochSecond(this.seconds),
        ZoneOffset.UTC
    ))