package com.example.todoer.wear.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.todoer.wear.domain.auth.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(LogInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { LogInState() }
    }
}