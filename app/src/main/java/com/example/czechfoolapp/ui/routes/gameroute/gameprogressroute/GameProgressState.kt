package com.example.czechfoolapp.ui.routes.gameroute.gameprogressroute

import androidx.compose.runtime.Immutable
import com.example.czechfoolapp.data.model.Player

@Immutable
data class GameProgressState(
    val players: List<Player>,
    val winnerID: Int?,
    val updatedPlayerIDs: Set<Int>,
    val isGameFinished: Boolean
) {
    val nextUserStep = if (winnerID == null) NextUserStep.SELECT_WINNER else NextUserStep.INPUT_PLAYER_CARDS
}


@Immutable
enum class NextUserStep(val title: String) {
    SELECT_WINNER("Select Winner"),
    INPUT_PLAYER_CARDS("Input Player Cards")
}