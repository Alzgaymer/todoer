package com.example.todoer.ui.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoer.ui.TodoerAppTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(onSuccess: () -> Unit) {
    Scaffold (
        topBar = { TodoerAppTopBar(
            canNavigateBack = false,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedButton(
            content = { Text("Log in")},
            onClick = onSuccess,
            modifier = Modifier.padding(it)
        )
    }
}
