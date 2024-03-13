package com.example.todoer.wear.navigation

sealed class Screens (val route: String){
    data object Main : Screens("main")
    data object Auth : Screens("auth")
}