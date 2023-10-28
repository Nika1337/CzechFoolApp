package com.example.czechfoolapp.ui.nameinputroute

import androidx.compose.runtime.Composable


@Composable
fun NameInputRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {
    
    NameInputScreen(
        onNavigateToNext = onNavigateToNext,
        onNavigateUp = onNavigateUp,
        onEvent = {},
        nameInputState = mapOf(1 to "Nika", 2 to "Taso", 3 to "Neka", 4 to ""),
        onValueChange = { i: Int, s: String -> }
    )
}

