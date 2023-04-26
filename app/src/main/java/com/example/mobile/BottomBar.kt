package com.example.mobile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Code: BottomBarScreen(
        route = "code",
        title = "Code",
        icon = Icons.Default.Settings
    )
    object Run: BottomBarScreen(
        route = "run",
        title = "Run",
        icon = Icons.Default.PlayArrow
    )
}