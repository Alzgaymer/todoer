package com.example.todoer.domain.todo

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TodosRepository {

    suspend fun getTodoes(userID: String, day: LocalDate): Flow<List<Todo>>

    fun setTodo(userID: String, todo: Todo)
}