package com.example.todoer.wear.service.todo

import android.util.Log
import com.example.todoer.domain.todo.serialize.TodoListSerializer
import com.example.todoer.wear.platform.repository.todo.add
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class TodoService : WearableListenerService() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "$TAG created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "$TAG destroyed")
    }

    override fun onDataChanged(events: DataEventBuffer) {
        super.onDataChanged(events)

        events.map { it.dataItem}
            .forEach { item ->
                when(item.uri.path) {
                    DAILY_TODOES -> {
                        val mapItem = DataMapItem.fromDataItem(item)
                        val json = mapItem.dataMap.getString("todoes")
                        val list = Json.decodeFromString(
                            deserializer = TodoListSerializer, json?: throw SerializationException())
                        add(list)
                    }
                }
            }
    }

    companion object {
        const val DAILY_TODOES = "/daily-todoes"
        private const val TAG = "TodoService"
    }
}