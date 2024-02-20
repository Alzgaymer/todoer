package com.example.todoer.platform.repositories.todo

import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.TodosRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class TodosRepositoryFirestoreImpl (
    private val firestore: FirebaseFirestore
) : TodosRepository {

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