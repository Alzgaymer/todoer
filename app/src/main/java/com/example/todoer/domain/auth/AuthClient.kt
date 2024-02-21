package com.example.todoer.domain.auth

import android.content.Intent
import android.content.IntentSender
import com.example.todoer.domain.user.User

interface AuthClient {

    suspend fun signIn(): IntentSender?

    suspend fun signInWithIntent(intent: Intent): SignInResult

    suspend fun signOut()

    fun getSignedInUser(): User?
}