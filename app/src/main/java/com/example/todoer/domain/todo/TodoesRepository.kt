package com.example.todoer.domain.todo

import kotlinx.coroutines.flow.Flow

interface TodoesRepository {

    suspend fun getTodoes(): Flow<List<Todo>>

    fun setTodo(userID: String, todo: Todo)
}