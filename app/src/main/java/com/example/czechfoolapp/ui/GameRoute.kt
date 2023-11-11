package com.example.czechfoolapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

@Composable
fun GameRoute(
    windowWidth: WindowWidthSizeClass,
    onCancel: () -> Unit
) {
    BackHandler {
        onCancel()
    }
}