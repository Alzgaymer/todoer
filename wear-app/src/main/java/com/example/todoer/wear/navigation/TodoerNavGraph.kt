package com.example.todoer.wear.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.domain.auth.SignInResult
import com.example.todoer.wear.presentation.auth.LogInScreen
import com.example.todoer.wear.presentation.auth.LogInViewModel
import com.example.todoer.wear.presentation.theme.TodoerTheme
import com.example.todoer.wear.presentation.todo.day.DayTodoes
import com.example.todoer.wear.presentation.todo.day.DayViewModel
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.AppScaffold
import kotlinx.coroutines.launch

@Composable
fun TodoerNavGraph(authClient: AuthClient, lifecycleScope: LifecycleCoroutineScope,  navController: NavHostController) {

}