package com.example.czechfoolapp.ui.nameinputroute

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

@Composable
fun NameInputRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {
    NameInputScreen(
        onNavigateUp = onNavigateUp,
        onNavigateToNext = onNavigateToNext
    )
}