package com.example.todoer.domain.auth

import com.example.todoer.domain.user.User

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)