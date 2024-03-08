package com.example.todoer.wear.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.navigation.TodoerNavGraph

@Composable
fun WearApp(authClient: AuthClient, lifecycleCoroutineScope: LifecycleCoroutineScope) {
    TodoerNavGraph(
        authClient = authClient,
        lifecycleScope = lifecycleCoroutineScope,
        navController = rememberSwipeDismissableNavController()
    )
}