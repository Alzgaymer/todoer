package com.example.todoer.wear.domain.todo.dto


import com.example.todoer.domain.todo.serialize.SerializableLatLng
import com.example.todoer.domain.todo.serialize.SerializableTimestamp
import com.example.todoer.wear.domain.todo.Todo
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class TodoJson (
    var userID: String,
    var startDateTime: SerializableTimestamp,
    var endDateTime: SerializableTimestamp,
    var remindMeOn: List<SerializableTimestamp>,
    var payload: String,
    var location: SerializableLatLng,
    var done: Boolean
)

fun TodoJson.toJson(): String = Json.encodeToString(this)

fun Todo.toJsonDTO(): TodoJson = TodoJson(
    userID= userID,
            startDateTime= SerializableTimestamp(startDateTime.seconds, startDateTime.nanoseconds),
            endDateTime= SerializableTimestamp(endDateTime.seconds, endDateTime.nanoseconds),
            remindMeOn= remindMeOn.map { SerializableTimestamp(it.seconds, it.nanoseconds) },
            payload= payload,
            location= SerializableLatLng(location.latitude, location.longitude),
            done= done,
)

fun TodoJson.toTodo(): Todo = Todo (
    userID= userID,
    startDateTime= Timestamp(startDateTime.seconds, startDateTime.nanoseconds),
    endDateTime= Timestamp(endDateTime.seconds, endDateTime.nanoseconds),
    remindMeOn= remindMeOn.map {Timestamp(it.seconds, it.nanoseconds) },
    payload= payload,
    location= LatLng(location.latitude, location.longitude),
    done= done,
)