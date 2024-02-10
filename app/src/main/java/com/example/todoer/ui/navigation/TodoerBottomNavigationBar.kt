package com.example.todoer.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoerBottomNavigationBar(navController: NavHostController) {
    //initializing the default selected item
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    NavigationBar {
        //getting the list of bottom navigation items for our data class
        getBottomNavigationItems().forEachIndexed { index, navigationItem ->
            //iterating all items with their respective indexes
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = {
                    Text(navigationItem.title)
                },
                icon = {
                    if (index == navigationSelectedItem)
                        Icon(
                            navigationItem.selectedIcon,
                            contentDescription = navigationItem.title
                        )
                    else
                        Icon(
                            navigationItem.unselectedIcon,
                            contentDescription = navigationItem.title
                        )
                },
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

