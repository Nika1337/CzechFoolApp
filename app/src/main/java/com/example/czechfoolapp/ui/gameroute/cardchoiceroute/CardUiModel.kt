package com.example.czechfoolapp.ui.gameroute.cardchoiceroute

import androidx.compose.runtime.Immutable
import com.example.czechfoolapp.data.model.Rank
import com.example.czechfoolapp.data.model.Suit

@Immutable
data class CardUiModel(
    val rank: Rank,
    val suits: Set<Suit>,
    val count: Int
) {
    init {
        require(count <= suits.size) { "Count must be less or equal to the number of suits." }
    }
    fun getScore() = getCardScore()*count

    private fun getCardScore() : Int {
        return if (rank == Rank.QUEEN) {
            if (suits.contains(Suit.HEARTS)) rank.point*2
            else rank.point
        } else {
            rank.point
        }
    }
}