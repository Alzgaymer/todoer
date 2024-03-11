package com.example.todoer.platform.service.watch.todo


import android.util.Log
import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.toJSON
import com.example.todoer.domain.watch.todo.SendTodoService
import com.example.todoer.domain.watch.todo.SendTodoService.PATHS.DAILY_TODOES
import com.example.todoer.domain.watch.todo.SendTodoService.PATHS.TAG
import com.example.todoer.domain.watch.todo.SendTodoService.PATHS.WEAR_CAPABILITY
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Node
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONArray
import javax.inject.Inject

class SendTodoServiceImpl @Inject constructor(
    private val messageClient: MessageClient,
    private val capabilityClient: CapabilityClient,
) : SendTodoService {

    override suspend fun send(list: List<Todo>) {

        val nodes = withContext(Dispatchers.IO) {
            capabilityClient
                .getCapability(WEAR_CAPABILITY, CapabilityClient.FILTER_REACHABLE)
                .await()
                .nodes
        }
        withContext(Dispatchers.IO) {
            nodes.map { node: Node? ->
                async {
                    node?.id?.let { messageClient.sendMessage(it, DAILY_TODOES, list.toJSON()) }
                }
            }.awaitAll()
        }
        Log.d(TAG, "message sended to weareable")
    }

}

private fun List<Todo>.toJSON():ByteArray {
    val jsonArray = JSONArray()
    forEach{ todo: Todo ->
        jsonArray.put(todo.toJSON())
    }
    return jsonArray.toString().toByteArray()
}