package com.example.todoer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoer.ui.calendar.WeekCalendarScreen
import com.example.todoer.ui.register.LogInScreen

//TODO: Add Authorization singleton service instance
@Composable
fun TodoerNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.Register.route) {
        composable(Screens.Register.route) {
            LogInScreen(onSuccess = {navController.navigateToCalendar()})
        }

        composable(Screens.Calendar.route) {
            WeekCalendarScreen(bottomBar = withBottomNavigationBar(navController))
        }
    }
}

private fun NavHostController.navigateToCalendar() {
    this.navigate(Screens.Calendar.route)
}

private fun withBottomNavigationBar(navController: NavHostController) : @Composable () -> Unit {
    return { TodoerBottomNavigationBar(navController) }
}




