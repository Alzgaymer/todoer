package com.example.todoer

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.todoer.domain.auth.AuthClient
import com.example.todoer.ui.navigation.TodoerNavHost

@Composable
fun TodoerApp(context: Context, authClient: AuthClient, lifecycleScope: LifecycleCoroutineScope) {
    TodoerNavHost(context, authClient, lifecycleScope, rememberNavController())
}