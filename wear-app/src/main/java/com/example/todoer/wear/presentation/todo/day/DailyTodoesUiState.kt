package com.example.todoer.wear.presentation.todo.day

import com.example.todoer.wear.domain.todo.Todo

data class DailyTodoesUiState(
    val list: List<Todo> = emptyList(),
    val userID: String = "",
) {
    companion object {
        const val NO_USER = "no_user"
    }
}
