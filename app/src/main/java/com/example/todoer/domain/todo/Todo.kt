package com.example.todoer.domain.todo

import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.example.todoer.ui.map.toGeoPoint
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import org.json.JSONObject
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

fun Todo.toJSON(): JSONObject {
    val jsonObject = JSONObject().apply {
        put("userID", userID)
        put(
            "startDateTime",
            startDateTime.toLocalDateTime().toEpochSecond(ZoneOffset.UTC)
        ) // Convert Timestamp to milliseconds
        put(
            "endDateTime",
            endDateTime.toLocalDateTime().toEpochSecond(ZoneOffset.UTC)
        ) // Convert Timestamp to milliseconds
        put(
            "remindMeOn",
            remindMeOn.map {
                it.toLocalDateTime().toEpochSecond(ZoneOffset.UTC)
            }) // Convert List<Timestamp> to List<Long> of milliseconds
        put("payload", payload)
        put("location", JSONObject().apply {
            put("latitude", location.latitude)
            put("longitude", location.longitude)
        })
        put("done", done)
    }
    return jsonObject
}

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
