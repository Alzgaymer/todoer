package com.example.todoer.wear.presentation.todo.day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.domain.todo.Todo
import com.example.todoer.wear.service.todo.TodoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val authClient: AuthClient,
    private val todoService: TodoService,
) : ViewModel() {

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
        viewModelScope.launch {
            todoService.todoes.collect { todoes: List<Todo> ->
                _uiState.update {
                    _uiState.value.copy(todoes = todoes)
                }
            }
        }
    }
}