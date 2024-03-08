package com.example.todoer.wear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.presentation.theme.TodoerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authClient: AuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TodoerTheme {
                WearApp(
                    authClient = authClient,
                    lifecycleCoroutineScope = lifecycleScope
                )
            }
        }
    }
}