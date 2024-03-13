package com.example.todoer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.todoer.domain.auth.AuthClient
import com.example.todoer.ui.theme.TodoerTheme
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var messageClient: MessageClient

    @Inject
    lateinit var capabilityClient: CapabilityClient

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    TodoerApp(
                        context = this.applicationContext,
                        authClient = authClient,
                        lifecycleScope = lifecycleScope,
                        navHostController = rememberNavController()
                    )
                }
            }
        }
    }
}

