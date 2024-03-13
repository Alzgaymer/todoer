package com.example.todoer.ui.todo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.format.DateTimeFormatter

@Composable
internal fun OnFieldError(error: String?, color: Color = Color.Unspecified, modifier: Modifier = Modifier) {
    error?.let { error ->
        Text(
            text = error,
            color = color,
            modifier = modifier
        )
    }
}

internal val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

