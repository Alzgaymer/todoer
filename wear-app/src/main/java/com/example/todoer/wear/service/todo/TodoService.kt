package com.example.todoer.wear.service.todo

import android.content.Intent
import android.util.Log
import com.example.todoer.wear.presentation.MainActivity
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

class TodoService @Inject constructor () : WearableListenerService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onDataChanged(p0: DataEventBuffer) {
        super.onDataChanged(p0)
        Log.d("TodoService", "data changed")
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)

        when (messageEvent.path) {
            START_ACTIVITY_PATH -> {
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
    companion object {
        private const val TAG = "TodoService"
        private const val START_ACTIVITY_PATH = "/start-activity"
        private const val DATA_ITEM_RECEIVED_PATH = "/data-item-received"
        const val DAILY_TODOES = "/daily-todoes"
    }
}