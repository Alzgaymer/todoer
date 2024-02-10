package com.example.todoer.ui.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoer.TodoerAppTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(onSuccess: () -> Unit) {
    Scaffold (
        topBar = { TodoerAppTopBar(
            canNavigateBack = false,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )},
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedButton(
            content = { Text("Log in")},
            onClick = onSuccess,
            modifier = Modifier.padding(it)
        )
    }
}