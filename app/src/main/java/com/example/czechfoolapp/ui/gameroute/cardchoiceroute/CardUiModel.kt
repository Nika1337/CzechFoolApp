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
        require(count < suits.size) { "Count must be less than the number of suits." }
    }
}