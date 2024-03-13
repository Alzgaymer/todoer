package com.example.todoer.ui.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
