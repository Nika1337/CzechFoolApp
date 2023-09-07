package com.example.czechfoolapp.ui.gameoptions

import androidx.compose.runtime.Composable

@Composable
fun GameOptionsRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {

    GameOptionsScreen(
        onNavigateUp = onNavigateUp,
        onNavigateToNext = onNavigateToNext
    )
}