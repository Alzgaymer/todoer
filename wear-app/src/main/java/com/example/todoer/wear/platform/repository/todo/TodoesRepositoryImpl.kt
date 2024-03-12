package com.example.todoer.wear.platform.repository.todo

import com.example.todoer.wear.domain.todo.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private val _list: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
val list: StateFlow<List<Todo>> = _list.asStateFlow()


fun add(list: List<Todo>) {
    _list.update { list }
}
