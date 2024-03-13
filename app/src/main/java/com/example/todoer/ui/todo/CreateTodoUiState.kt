package com.example.todoer.ui.todo

import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

data class CreateTodoUiState(
    val currentDate: LocalDate = LocalDate.now(),
    var location: LatLng = LatLng(0.0,0.0)
)
