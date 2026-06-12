package com.example.babybillyvn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.babybillyvn.data.repository.BabyBillyRepository
import com.example.babybillyvn.ui.screens.CommunityScreen
import com.example.babybillyvn.ui.screens.HomeScreen
import com.example.babybillyvn.ui.screens.ProfileScreen
import com.example.babybillyvn.ui.screens.WeekScreen
import com.example.babybillyvn.ui.theme.BabyBillyVNTheme
import com.example.babybillyvn.ui.theme.TextDark
import com.example.babybillyvn.ui.theme.TextLight
import com.example.babybillyvn.viewmodel.MainViewModel
import com.example.babybillyvn.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val repository = BabyBillyRepository(this)
            val viewModel: MainViewModel = viewModel(factory = ViewModelFactory(repository))
            
            BabyBillyVNTheme {
                MainApp(viewModel)
            }
        }
    }
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector, val selectedIcon: ImageVector) {
    object Home : Screen("home", "Trang chủ", Icons.Outlined.Home, Icons.Filled.Home)
    object Week : Screen("week", "Tuần này", Icons.Outlined.CalendarToday, Icons.Filled.CalendarToday)
    object Community : Screen("community", "Góc chia sẻ", Icons.Outlined.Forum, Icons.Filled.Forum)
    object Profile : Screen("profile", "Cá nhân", Icons.Outlined.Person, Icons.Filled.Person)
}

@Composable
fun MainApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Week, Screen.Community, Screen.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        icon = { Icon(if (selected) screen.selectedIcon else screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextDark,
                            unselectedIconColor = TextLight,
                            selectedTextColor = TextDark,
                            unselectedTextColor = TextLight,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { HomeScreen(viewModel) }
            composable(Screen.Week.route) { WeekScreen(viewModel) }
            composable(Screen.Community.route) { CommunityScreen(viewModel) }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}
