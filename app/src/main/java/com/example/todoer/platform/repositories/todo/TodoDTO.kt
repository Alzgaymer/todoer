package com.example.todoer.platform.repositories.todo

import com.example.todoer.domain.todo.Todo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import java.time.LocalDate
import java.time.ZoneId

data class TodoDTO(
    var userID: String? = null,
    var startDate: Timestamp? = null,
    var endDate: Timestamp? = null,
    var remindMeOn: List<Timestamp>? = null,
    var payload: String? = null,
    var location: GeoPoint? = null,
)

fun Timestamp.toLocalDate(): LocalDate =
     this.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()




fun TodoDTO.toTodo(): Todo {
    return Todo(
        userID ?: "", // If userID is null, set it to an empty string
        startDate?.toLocalDate() ?: LocalDate.MIN, // Convert startDate to LocalDate or set it to LocalDate.MIN if null
        endDate?.toLocalDate() ?: LocalDate.MIN, // Convert endDate to LocalDate or set it to LocalDate.MIN if null
        remindMeOn?.map { it.toLocalDate() } ?: emptyList(), // Convert remindMeOn to List<LocalDate> or set it to an empty list if null
        payload ?: "", // If payload is null, set it to an empty string
        //Location() // Location is already nullable, so no conversion needed
    )
}
