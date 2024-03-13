package com.example.todoer.wear.domain.auth

import androidx.activity.result.contract.ActivityResultContract
import com.example.todoer.wear.domain.user.User

abstract class AuthClient : ActivityResultContract<Unit, SignInResult>() {
    abstract val user: User?

    abstract fun getSignedInUser(): User?

    abstract suspend fun signOut()
}