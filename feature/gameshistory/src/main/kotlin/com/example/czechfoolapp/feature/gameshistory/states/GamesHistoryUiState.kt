package com.example.czechfoolapp.feature.gameshistory.states

import androidx.compose.runtime.Immutable
import com.example.czechfoolapp.core.model.Game


@Immutable
data class GamesHistoryUiState(
    val games: List<Game>
)
