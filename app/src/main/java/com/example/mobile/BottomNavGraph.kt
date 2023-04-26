package com.example.mobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val textList = remember { mutableStateOf(emptyList<String>()) }
    val blockList = remember { mutableStateOf(emptyList<CodeBlock>()) }

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Code.route
    ) {
        composable(route = BottomBarScreen.Code.route) {
            CodeScreen(blockList = blockList)
        }

        composable(route = BottomBarScreen.Run.route) {
            RunScreen(textList = textList, blockList = blockList)
        }
    }
}
