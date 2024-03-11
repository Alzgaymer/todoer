package com.example.todoer.wear.platform.auth.google

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.domain.auth.SignInResult
import com.example.todoer.wear.domain.user.User
import com.example.todoer.wear.domain.user.toUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthClient(
    context: Context
) : AuthClient() {

    override var user: User? = null

    private val client = GoogleSignIn.getClient(
        /* context = */context,
        /* options = */GoogleSignInOptions.Builder(DEFAULT_SIGN_IN)
            .requestId()
            .build())

    override fun getSignedInUser(): User? = user

    override suspend fun signOut() {
        try {
            client.signOut().await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    override fun createIntent(context: Context, input: Unit): Intent {
        return client.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): SignInResult {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        // As documented, this task must be complete
        check(task.isComplete)

        return if (task.isSuccessful) {
            SignInResult(
                data = task.result.toUser().also {
                    user = it
                },
                errorMessage = null
            )
        } else {
            val exception = task.exception
            check(exception is ApiException)
            val msg = "Sign in failed: code=${
                exception.statusCode
            }, message=${
                GoogleSignInStatusCodes.getStatusCodeString(exception.statusCode)
            }"

            Log.w(
                "GoogleSignInContract",
                msg
            )

            SignInResult(
                data = null,
                errorMessage = msg
            )
        }
    }
}
