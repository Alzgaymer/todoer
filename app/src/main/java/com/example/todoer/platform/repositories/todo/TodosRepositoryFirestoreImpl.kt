package com.example.todoer.platform.repositories.todo

import android.util.Log
import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.TodosRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class TodosRepositoryFirestoreImpl @Inject constructor () : TodosRepository {

    private val firestore = Firebase.firestore
    private val todosCollection = firestore.collection(consts.COLLECTION_PATH)

    override suspend fun getTodoes(userID: String, day: LocalDate): Flow<List<Todo>> = flow {
        try {
            val result = todosCollection
                .whereEqualTo("userID", userID)
                .whereGreaterThanOrEqualTo("startDate", day.toTimestamp())
                .get()
                .await()

            when {
                result.isEmpty -> throw Exception()
            }

            val todoList = mutableListOf<Todo>()

            for (document in result.documents) {
                val todo = document.toObject(TodoDTO::class.java)
                todo?.takeIf { todo -> todo.endDate?.toLocalDate()?.atStartOfDay() == day.nextDay().atStartOfDay() }
                    ?.also { todoList.add(it.toTodo()) }
            }

            // Emit the list of todos
            emit(todoList)
        } catch (e: Exception) {

            e.printStackTrace()
            if(e is CancellationException) throw e

            // Example: Log error
            e.message?.let{ Log.d(consts.TAG, it )}
            // You can emit an empty list or throw the exception depending on your use case
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)


    override fun setTodo(userID: String, todo: Todo) {
        TODO("Not yet implemented")
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