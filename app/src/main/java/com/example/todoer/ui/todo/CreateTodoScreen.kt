package com.example.todoer.ui.todo

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoer.ui.TodoerAppTopBar
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodoScreen(
    selectedDate: LocalDate,
    viewModel: CreateTodoViewModel = hiltViewModel(),
    onBackButton: () -> Unit
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect{ event ->
            when (event) {
                is CreateTodoViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context,"Todo created!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = { TodoerAppTopBar(
            canNavigateBack = true,
            navigateUp = onBackButton,
            scrollBehavior = scrollBehavior
        )}
    ) { contentPadding ->
        TodoForm(
            state = state,
            payloadValueChange = viewModel::payloadChange,
            startDateValueChange = {},
            endDateValueChange = {},
            remindMeOnValueChange = {},
            contentPadding = contentPadding
        )
    }
}

@Composable
fun TodoForm(
    state: CreateTodoState,
    payloadValueChange: (String) -> Unit,
    startDateValueChange: () -> Unit,
    endDateValueChange: () -> Unit,
    remindMeOnValueChange: () -> Unit,
    contentPadding: PaddingValues
) {
    // Payload
    TextField(
        value = state.payload,
        onValueChange = payloadValueChange,
        isError = state.payloadError != null,
        placeholder = {Text(text = "Todo")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth(),
    )
    if (state.payloadError != null) {
        Text(
            text = state.payloadError,
            color = MaterialTheme.colorScheme.error,
        )
    }


}