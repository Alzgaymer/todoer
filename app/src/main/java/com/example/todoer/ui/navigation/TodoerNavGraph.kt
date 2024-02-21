package com.example.todoer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoer.ui.auth.LogInScreen
import com.example.todoer.ui.calendar.WeekCalendarScreen

@Composable
fun TodoerNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.Register.route) {
        composable(Screens.Register.route) {
            LogInScreen(onSuccess = { navController.navigateToCalendar() })
        }

        composable(Screens.Calendar.route) {
            WeekCalendarScreen(navController)
        }
    }
}

private fun NavHostController.navigateToCalendar() {
    this.navigate(Screens.Calendar.route)
}
