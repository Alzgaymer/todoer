package com.example.todoer.wear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.service.todo.TodoService
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val messageClient by lazy { Wearable.getMessageClient(this) }

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var todoService: TodoService


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp(
                authClient = authClient,
                lifecycleCoroutineScope = lifecycleScope
            )
        }
    }

    override fun onResume() {
        super.onResume()
        messageClient.addListener { todoService }
    }

    override fun onPause() {
        super.onPause()
        messageClient.removeListener { todoService }
    }
}