package com.example.todoer.ui.navigation

sealed class Screens(val route : String) {
    object Register : Screens("register")
    object Calendar : Screens("calendar")
    object Settings : Screens("settings")
}