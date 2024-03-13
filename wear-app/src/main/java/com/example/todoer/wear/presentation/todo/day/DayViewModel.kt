package com.example.todoer.wear.presentation.todo.day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.platform.repository.todo.TodoesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            TodoesRepository.list.collect {
                withContext(Dispatchers.Main) {
                    updateState(DailyTodoesUiState(list = it))
                }
            }
        }
    }

    fun updateState(uiState: DailyTodoesUiState) {
        _uiState.update {uiState}
    }
}