package com.example.todoer.domain.todo

import java.util.UUID

interface TodosRepository {
    suspend fun getTodoes(userID: UUID): List<Todo>
    fun setTodo(userID: UUID, todo: Todo)
}