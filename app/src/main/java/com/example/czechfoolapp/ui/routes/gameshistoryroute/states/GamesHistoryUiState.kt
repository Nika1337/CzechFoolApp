package com.example.czechfoolapp.ui.routes.gameshistoryroute.states

import androidx.compose.runtime.Immutable
import com.example.czechfoolapp.data.model.Game


@Immutable
data class GamesHistoryUiState(
    val games: List<Game>
)
