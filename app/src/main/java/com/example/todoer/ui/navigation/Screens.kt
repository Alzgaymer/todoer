package com.example.todoer.ui.navigation

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
}