package com.example.todoer.wear.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.domain.auth.SignInResult
import com.example.todoer.wear.navigation.Screens
import com.example.todoer.wear.presentation.auth.LogInScreen
import com.example.todoer.wear.presentation.auth.LogInViewModel
import com.example.todoer.wear.presentation.theme.TodoerTheme
import com.example.todoer.wear.presentation.todo.day.DayTodoes
import com.example.todoer.wear.presentation.todo.day.DayViewModel
import com.example.todoer.wear.service.todo.TodoService
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.Wearable
import com.google.android.horologist.compose.layout.AppScaffold
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(),
DataClient.OnDataChangedListener {

    private val dataClient by lazy { Wearable.getDataClient(this) }

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var todoService: TodoService

    private val dayViewModel: DayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            val navController = rememberNavController()
            TodoerTheme {
                AppScaffold {
                    NavHost(navController = navController, startDestination = Screens.Main.route) {
                        composable(Screens.Main.route) {

                            val uiState by dayViewModel.uiState.collectAsStateWithLifecycle()

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
    }

    override fun onResume() {
        super.onResume()
        dataClient.addListener(this)
    }

    override fun onPause() {
        super.onPause()
        dataClient.removeListener(this)
    }

    override fun onDataChanged(events: DataEventBuffer) {
        events.map { it.dataItem}
            .forEach { item ->
                Log.d("P", item.data.toString())
                //on collect update ui state
            }
    }
}