package com.example.todoer.ui.navigation

sealed class Screens(val route : String) {
    data object Auth : Screens("auth")
    data object Calendar : Screens("calendar")
    data object Settings : Screens("settings")
}