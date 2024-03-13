package com.example.todoer.domain.watch.todo

import com.example.todoer.domain.todo.Todo

interface SendTodoService {

    suspend fun send(list: List<Todo>)

    companion object PATHS {
        const val DAILY_TODOES = "/daily-todoes"
        const val WEAR_CAPABILITY = "wear"
    }
}