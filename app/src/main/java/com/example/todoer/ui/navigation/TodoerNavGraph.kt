package com.example.todoer.ui.navigation

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoer.domain.auth.AuthClient
import com.example.todoer.ui.auth.SignInScreen
import com.example.todoer.ui.auth.SignInViewModel
import com.example.todoer.ui.calendar.WeekCalendarScreen
import com.example.todoer.ui.map.MapScreen
import com.example.todoer.ui.map.MapViewModel
import com.example.todoer.ui.todo.CreateTodoScreen
import com.example.todoer.ui.todo.CreateTodoViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TodoerNavHost(
    context: Context,
    authClient: AuthClient,
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screens.Auth.route) {
        composable(Screens.Auth.route) {
            val viewModel: SignInViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if (authClient.getSignedInUser() != null) {
                    navController.navigateToCalendar()
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = authClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigateToCalendar()
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    lifecycleScope.launch {
                        val signInIntentSender = authClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }

        composable(Screens.WeekCalendar.route) {
            WeekCalendarScreen(navController = navController)
        }

        composable(Screens.CreateTodo.route) { backStackEntry ->

            val viewModel: CreateTodoViewModel = hiltViewModel()
            val uiState by viewModel.state.collectAsStateWithLifecycle()

            backStackEntry.savedStateHandle.getLiveData<Double>("longitude")
                .observeForever { viewModel.longitudeChanged(it) }
            backStackEntry.savedStateHandle.getLiveData<Double>("latitude")
                .observeForever { viewModel.latitudeChanged(it) }

            val dateStr = backStackEntry.arguments?.getString("date")
            val date = LocalDate.parse(dateStr)
            viewModel.dateChanged(date)

            CreateTodoScreen(
                uiState = uiState,
                onMapNavigate = navController::navigateToMap ,
                onBackButton = navController::navigateToCalendar,
                onPayloadChange = viewModel::payloadChange,
                onStartTimeChange = viewModel::startTimeChange,
                onEndTimeChange = viewModel::endTimeChange,
                onSubmitEvent = viewModel::submitEvent,
                onRemindmeOnValueChange = viewModel::remindmeOnValueChange,
                onRemindmeOnDelete = viewModel::remindmeOnDelete
            )
        }

        composable(Screens.Map.route) { stack ->
            val viewModel: MapViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            MapScreen(state, navigateUp = {navController.navigateUp()},
            onLongClick = {viewModel.deleteLocation()}
            ) {
                navController.previousBackStackEntry
                    ?.savedStateHandle?.set("latitude", it.latitude)
                navController.previousBackStackEntry
                    ?.savedStateHandle?.set("longitude", it.longitude)

                viewModel.setLocation(it.latitude, it.longitude)
            }
        }
    }
}

private fun NavHostController.navigateToCalendar() =
    this.navigate(Screens.WeekCalendar.route)

fun NavHostController.navigateToCreateToDo(date: LocalDate) =
    this.navigate(Screens.CreateTodo.createRoute(date))

private fun NavHostController.navigateToMap(latLng: LatLng) =
    this.navigate(Screens.Map.createRoute(latLng))