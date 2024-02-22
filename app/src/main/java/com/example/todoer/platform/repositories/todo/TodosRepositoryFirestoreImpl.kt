package com.example.todoer.platform.repositories.todo

import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.TodosRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class TodosRepositoryFirestoreImpl @Inject constructor () : TodosRepository {

    private val firestore = Firebase.firestore

    override suspend fun getTodoes(userID: UUID): List<Todo> = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override fun setTodo(userID: UUID, todo: Todo) {
        TODO("Not yet implemented")
    }


    private object consts {
        const val COLLECTION_PATH = "todoes"
    }
}