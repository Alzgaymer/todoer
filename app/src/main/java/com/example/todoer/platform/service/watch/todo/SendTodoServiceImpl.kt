package com.example.todoer.platform.service.watch.todo


import android.util.Log
import com.example.todoer.domain.todo.Todo
import com.example.todoer.domain.todo.serialize.TodoListSerializer
import com.example.todoer.domain.watch.todo.SendTodoService
import com.example.todoer.domain.watch.todo.SendTodoService.PATHS.DAILY_TODOES
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.PutDataMapRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SendTodoServiceImpl @Inject constructor(
    private val messageClient: MessageClient,
    private val dataClient: DataClient,
    private val capabilityClient: CapabilityClient,
) : SendTodoService {

    val TAG: String = "SendTodoServiceImpl"

    override suspend fun send(list: List<Todo>): Unit = withContext(Dispatchers.IO) {
        try {
            val json = Json.encodeToString(serializer = TodoListSerializer, value = list)
            val request = PutDataMapRequest.create(DAILY_TODOES).apply {
                dataMap.putString("todoes", json)
            }
                .asPutDataRequest()

            val result = dataClient.putDataItem(request).await()

            Log.d(TAG, "message sended to weareable, $json, posted at: ${result.uri}")
        }catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }

    }


}
