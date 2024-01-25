package com.example.czechfoolapp.ui.gameroute

import androidx.activity.compose.BackHandler
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameRoute(
    windowWidth: WindowWidthSizeClass,
    onCancel: () -> Unit
) {
    BackHandler{
        // Shouldn't do anything
    }
    val viewModel: GameViewModel = viewModel(factory = GameViewModel.factory)
}