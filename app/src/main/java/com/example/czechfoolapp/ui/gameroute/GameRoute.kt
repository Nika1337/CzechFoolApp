package com.example.czechfoolapp.ui.gameroute

import androidx.activity.compose.BackHandler
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

@Composable
fun GameRoute(
    windowWidth: WindowWidthSizeClass,
    onCancel: () -> Unit
) {
    BackHandler{
        // Shouldn't do anything
    }
}