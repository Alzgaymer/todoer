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

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun TodoerNavGraph(authClient: AuthClient, lifecycleScope: LifecycleCoroutineScope,  navController: NavHostController) {
    TodoerTheme {
        AppScaffold {
            NavHost(navController = navController, startDestination = Screens.Auth.route) {
                composable(Screens.Main.route) {
                    val viewModel: DayViewModel = hiltViewModel()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    DayTodoes(uiState)
                }

                composable(Screens.Auth.route) {
                    val viewModel: LogInViewModel = hiltViewModel()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    LaunchedEffect(key1 = Unit) {
                        if (authClient.getSignedInUser() != null) {
                            navController.navigate(Screens.Main.route)
                        }
                    }

                    val launcher = rememberLauncherForActivityResult(
                        contract = authClient,
                    ) { result: SignInResult ->
                        lifecycleScope.launch {
                            viewModel.onSignInResult(result)
                        }
                    }

                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if (state.isSignInSuccessful) {
                            navController.navigate(Screens.Main.route)
                            viewModel.resetState()
                        }
                    }

                    LogInScreen(state = state) {
                        lifecycleScope.launch {
                            launcher.launch(Unit)
                        }
                    }
                }
            }
        }
    }
}