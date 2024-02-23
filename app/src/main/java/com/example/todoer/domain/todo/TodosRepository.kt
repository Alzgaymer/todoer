package com.example.todoer.domain.todo

import java.time.LocalDate

interface TodosRepository {

    suspend fun getTodoes(userID: String, day: LocalDate): List<Todo>

    fun setTodo(userID: String, todo: Todo)
}