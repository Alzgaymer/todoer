package com.example.todoer.ui.navigation

import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

sealed class Screens(val route : String) {
    data object Auth : Screens("auth")
    data object WeekCalendar : Screens("calendar")
    data object Settings : Screens("settings")
    data object CreateTodo: Screens("todo/create/{date}") {
        fun createRoute(date: LocalDate): String {
            return "todo/create/$date"
        }
    }

    data object Map : Screens("map/{latitude}/{longitude}") {
        fun createRoute(latLng: LatLng): String {
            return "map/${latLng.latitude}/${latLng.longitude}"
        }
    }
}