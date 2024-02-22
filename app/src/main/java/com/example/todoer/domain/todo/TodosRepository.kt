package com.example.todoer.domain.todo

interface TodosRepository {

    suspend fun getTodoes(userID: String): List<Todo>

    fun setTodo(userID: String, todo: Todo)
}