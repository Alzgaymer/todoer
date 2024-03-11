package com.example.todoer.wear.service.todo

import com.example.todoer.wear.domain.todo.Todo
import com.example.todoer.wear.domain.todo.toTodo
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONArray
import javax.inject.Inject

class TodoService @Inject constructor () : WearableListenerService() {

    private val _todoes: MutableStateFlow<List<Todo>>
        = MutableStateFlow(emptyList())
    val todoes: StateFlow<List<Todo>> = _todoes.asStateFlow()

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)

        when(messageEvent.path) {
            DAILY_TODOES -> {
                _todoes.update {
                    val list: MutableList<Todo> = mutableListOf()
                    try {
                        // json parse messageEvent.data in list
                        val json = JSONArray(messageEvent.data)
                        for(i in 0 until json.length()) {
                            val jsonObject = json.getJSONObject(i)
                            list.add(jsonObject.toTodo())
                        }
                        return@update list
                    } catch (e : Exception) {
                        e.printStackTrace()
                        return@update emptyList()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "TodoService"

        const val DAILY_TODOES = "/daily-todoes"
    }
}