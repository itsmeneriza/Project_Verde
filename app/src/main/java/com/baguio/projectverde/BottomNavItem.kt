package com.baguio.projectverde

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: @Composable () -> ImageVector
)

fun getBottomNavItems() = listOf(
    BottomNavItem("Home", "Home", { Icons.Default.Home }),
    BottomNavItem("Challenges", "Challenges", { Icons.Default.ThumbUp }),
    BottomNavItem("Scan", "Scan", { AppIcons.QrCode }),
    BottomNavItem("Rewards", "Rewards", { Icons.Default.Star }),
    BottomNavItem("InfoHub", "InfoHub", { Icons.Default.Info })
)
