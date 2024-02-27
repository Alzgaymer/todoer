package com.example.todoer.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoer.domain.auth.AuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authClient: AuthClient,
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch {
            authClient.signOut()
        }
    }
}