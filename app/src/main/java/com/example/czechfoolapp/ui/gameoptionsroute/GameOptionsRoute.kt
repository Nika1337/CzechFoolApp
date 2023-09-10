package com.example.czechfoolapp.ui.gameoptionsroute

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