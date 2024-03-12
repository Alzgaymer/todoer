package com.example.todoer.wear.domain.todo

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp

import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Todo (
    var userID: String,
    var startDateTime: Timestamp,
    var endDateTime: Timestamp,
    var remindMeOn: List<Timestamp>,
    var payload: String,
    var location: LatLng,
    var done: Boolean
)

fun JSONObject.toTodo(): Todo {
    return Todo(
        userID = getString("userID"),
        startDateTime =getString("startDate").toTimestamp(),
        endDateTime =getString("endDate").toTimestamp(),
        remindMeOn = getJSONArray("remindMeOn").toTimestampList(),
        payload =getString("payload"),
        location =getJSONObject("location").toLatLng(),
        done =getBoolean("done"),
    )
}

private fun String.toTimestamp(): Timestamp {
    val inst = LocalDateTime.parse(this)
    val seconds = inst.toEpochSecond(ZoneOffset.UTC)
    val nano = inst.toInstant(ZoneOffset.UTC).nano
    return Timestamp(seconds, nano)
}

private fun JSONObject.toLatLng(): LatLng {
    val latitude = getDouble("latitude")
    val longitude = getDouble("longitude")
    return LatLng(latitude, longitude)
}

private fun JSONArray.toTimestampList(): List<Timestamp>  =
    try {
        val list = mutableListOf<Timestamp>()
        for (i in 0 until length()) {
            val str = getString(i)
            list.add(str.toTimestamp())
        }
         list
    } catch (e : Exception) {
        e.printStackTrace()
        emptyList()
    }

fun mapTodoes(json: JSONArray): List<Todo> {
    val list = mutableListOf<Todo>()
    for (i in 0 until json.length()){
        val todo = json.getJSONObject(i).toTodo()
        list.add(todo)
    }
    return list
}