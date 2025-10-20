package com.baguio.projectverde

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.baguio.projectverde.ui.theme.ProjectVerdeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectVerdeTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val navItems = getBottomNavItems()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, navItems)
        }
    ) { padding ->
        NavigationHost(navController, modifier = Modifier.padding(padding))
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "InfoHub",
        modifier = modifier
    ) {
        composable("InfoHub") {
            InfoHubScreen()
        }
        composable("Rewards") {
            RewardsScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon = { Icon(item.icon(), contentDescription = item.name) },
                label = { Text(item.name) },
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Preview(showSystemUi = true, name = "Full App Scaffold Preview")
@Composable
fun MainAppScreenPreview() {
    val navItems = getBottomNavItems()

    ProjectVerdeTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = rememberNavController(), items = navItems)
            }
        ) { padding ->
            Text(
                text = "Bottom Bar Structure Only",
                modifier = Modifier.padding(padding).fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
    }
}