package com.example.todoer.domain.user

import android.net.Uri

data class User(
    val userID: String,
    val username: String?,
    val profilePicture: Uri?
)
