package com.example.todoer.wear.domain.auth

import com.example.todoer.wear.domain.user.User

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)