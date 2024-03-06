package com.example.todoer

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavHostController
import com.example.todoer.domain.auth.AuthClient
import com.example.todoer.ui.navigation.TodoerNavHost

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TodoerApp(
    context: Context,
    authClient: AuthClient,
    lifecycleScope: LifecycleCoroutineScope,
    navHostController: NavHostController
) {
    TodoerNavHost(context, authClient, lifecycleScope, navHostController)
}