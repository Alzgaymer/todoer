package com.example.todoer.wear.presentation.auth

data class LogInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
