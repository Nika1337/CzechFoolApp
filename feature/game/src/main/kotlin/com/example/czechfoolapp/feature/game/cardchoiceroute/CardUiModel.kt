package com.example.czechfoolapp.feature.game.cardchoiceroute

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.czechfoolapp.core.model.Rank
import com.example.czechfoolapp.core.model.Suit
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class CardUiModel(
    val rank: Rank,
    val suits: Set<Suit>,
    val count: Int = 0
) : Parcelable {
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