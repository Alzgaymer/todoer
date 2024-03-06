package com.example.todoer.domain.todo

import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.example.todoer.ui.map.toGeoPoint
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
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

fun Todo.toHashMap(): HashMap<String, Any> = hashMapOf(
    "userID" to userID,
    "startDate" to startDateTime,
    "endDate" to endDateTime,
    "remindMeOn" to remindMeOn,
    "payload" to payload,
    "location" to location.toGeoPoint(),
    "done" to done,
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

fun Todo.remindMeOn(): String = remindMeOn.joinToString {
    it.toLocalDateTime().toLocalTime().format(hourFormatter)
}
