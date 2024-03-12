package com.example.todoer.wear.domain.todo

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp

data class Todo (
    var userID: String,
    var startDateTime: Timestamp,
    var endDateTime: Timestamp,
    var remindMeOn: List<Timestamp>,
    var payload: String,
    var location: LatLng,
    var done: Boolean
)