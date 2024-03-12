package com.example.todoer.wear.presentation.todo.day

import androidx.lifecycle.ViewModel
import com.example.todoer.wear.domain.auth.AuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val authClient: AuthClient,
) : ViewModel(){

    private val _uiState: MutableStateFlow<DailyTodoesUiState>
        = MutableStateFlow(DailyTodoesUiState())

    val uiState: StateFlow<DailyTodoesUiState> =
        _uiState.asStateFlow()

    init {
        _uiState.update {
            DailyTodoesUiState(
                userID = authClient.user?.userID ?: DailyTodoesUiState.NO_USER
            )
        }
    }

    fun updateState(uiState: DailyTodoesUiState) {
        _uiState.update {uiState}
    }
}