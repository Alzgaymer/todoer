package com.example.todoer.wear.presentation.todo.day

import androidx.lifecycle.ViewModel
import com.example.todoer.wear.domain.auth.AuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val authClient: AuthClient
) : ViewModel() {

    fun getUserID(): String {
        return authClient.getSignedInUser()?.userID?: "No user id"
    }
}