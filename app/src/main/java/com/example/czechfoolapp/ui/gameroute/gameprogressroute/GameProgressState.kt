package com.example.czechfoolapp.ui.gameroute.gameprogressroute

import com.example.czechfoolapp.data.model.Player

data class GameProgressState(
    val players: List<Player>,
    val winnerID: Int?,
    val updatedPlayerIDs: Set<Int>
) {
    val nextUserStep = if (winnerID == null) NextUserStep.SELECT_WINNER else NextUserStep.INPUT_PLAYER_CARDS
}


enum class NextUserStep(val title: String) {
    SELECT_WINNER("Select Winner"),
    INPUT_PLAYER_CARDS("Input Player Cards")
}