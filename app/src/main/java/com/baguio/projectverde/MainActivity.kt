package com.baguio.projectverde

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
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

    // LOGIC: Hide Bottom Bar on Splash and Auth screens
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute !in listOf("Splash", "Auth")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController, navItems)
            }
        }
    ) { padding ->
        NavigationHost(navController, modifier = Modifier.padding(padding))
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "Splash", // Start at Splash
        modifier = modifier
    ) {
        // --- New Onboarding Flow ---
        composable("Splash") {
            SplashScreen(navController)
        }
        composable("Auth") {
            AuthScreen(navController)
        }

        // --- Main App Tabs ---
        composable("Home") {
            DashboardScreen(navController)
        }
        composable("InfoHub") {
            InfoHubScreen()
        }
        composable("Rewards") {
            RewardsScreen()
        }

        // Placeholders for other tabs
        composable("Challenges") { Text("Challenges Screen", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) }
        composable("Scan") { Text("QR Scanner Screen", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) }
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
                label = {
                    // FIX 2 APPLIED: Smaller Font, but kept labels visible
                    Text(
                        text = item.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelSmall // <--- Forces smaller text
                    )
                },
                selected = isSelected,

                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("Home") {
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