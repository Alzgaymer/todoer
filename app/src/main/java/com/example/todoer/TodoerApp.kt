package com.example.todoer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.todoer.ui.navigation.TodoerNavHost

@Composable
fun TodoerApp() {
    TodoerNavHost(rememberNavController())
}

