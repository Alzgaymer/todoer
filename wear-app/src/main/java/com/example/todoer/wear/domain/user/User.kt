package com.example.todoer.wear.domain.user

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

data class User(
    val userID: String,
    val username: String?,
    val profilePicture: Uri?
)

fun FirebaseUser?.toUser(): User? {
    return if (this == null) null
    else User(
        userID = this.uid ,
        username = this.displayName,
        profilePicture = this.photoUrl
    )
}

fun GoogleSignInAccount?.toUser(): User? {
    return if (this == null) null
    else User(
        userID= this.id?: "",
        username= this.displayName,
        profilePicture= this.photoUrl,
    )
 }