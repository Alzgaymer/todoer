package com.example.todoer.platform.repositories.todo

import android.util.Log
import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.TodoesRepository
import com.example.todoer.domain.todo.toHashMap
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import javax.inject.Inject

class TodosRepositoryFirestoreImpl @Inject constructor (
    private val firestore: FirebaseFirestore
) : TodoesRepository {
    private val todosCollection = firestore.collection(consts.COLLECTION_PATH)

    override suspend fun getTodoes(): Flow<List<Todo>> = callbackFlow<List<Todo>> {
        try {
            todosCollection.addSnapshotListener { snap, e ->
                e?.let { close(e); return@addSnapshotListener }

                if (snap != null && !snap.isEmpty) {
                    val list: MutableList<Todo> = mutableListOf()
                    for (document in snap.documents) {
                        val todo = document.toObject(TodoDTO::class.java)
                        todo?.let {
                                list.add(it.toTodo())
                            }
                    }
                    trySendBlocking(list)
                }
            }

            while (isActive) {}

            awaitClose()
        } catch (e: Exception) {

            e.printStackTrace()
            if(e is CancellationException) close(e)

            // Example: Log error
            e.message?.let{ Log.d(consts.TAG, it )}
            // You can emit an empty list or throw the exception depending on your use case
            trySendBlocking(emptyList())
                .onFailure { close(e) }
        }
    }.flowOn(Dispatchers.IO)




    override fun setTodo(userID: String, todo: Todo) {
        val todoMap = todo.toHashMap()

        firestore.collection(consts.COLLECTION_PATH)
            .document()
            .set(todoMap)
            .addOnSuccessListener {
                println("Todo added successfully!")
            }
            .addOnFailureListener { e ->
                println("Error adding todo: $e")
            }
    }


    private object consts {
        const val COLLECTION_PATH = "todoes"
        val TAG = TodosRepositoryFirestoreImpl::class.toString()
    }
}

fun LocalDate.toTimestamp(): Timestamp {
    val date = Date.from(this.atStartOfDay().toInstant(ZoneOffset.UTC))
    return Timestamp(date)
}

fun LocalDate.nextDay(): LocalDate {
    this.plusDays(1)
    return this
}