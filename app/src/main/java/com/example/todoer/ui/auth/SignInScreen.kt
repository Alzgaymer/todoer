package com.example.todoer.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onSignInClick) {
            Text(text = "Sign in")
        }
    }
}

//Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Sign In",
//            style = MaterialTheme.typography.labelLarge,
//            modifier = Modifier.padding(vertical = 32.dp)
//        )
//        val focusRequester by remember { mutableStateOf(FocusRequester()) }
//
//        TextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//            keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        )
//
//        TextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password") },
//            singleLine = true,
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//                .focusRequester(focusRequester)
//        )
//        Button(
//            onClick = {
//                // Perform sign in logic here
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp)
//        ) {
//            Text("Sign In")
//        }
//        TextButton(
//            onClick = onSignInClick
//        ) {
//            Text("Don't have an account? Sign up")
//        }
//    }