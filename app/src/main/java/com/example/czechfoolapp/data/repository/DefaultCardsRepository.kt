package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Card
import com.example.czechfoolapp.data.model.Rank
import com.example.czechfoolapp.data.model.Suit
import javax.inject.Inject

class DefaultCardsRepository @Inject constructor() : CardsRepository {
    override val winnerCards = listOf(
        listOf(
            Card(
                rank = Rank.QUEEN,
                suit = Suit.HEARTS
            )
        ),
        listOf(
            Card(
                rank = Rank.QUEEN,
                suit = Suit.CLUBS
            ),
            Card(
                rank = Rank.QUEEN,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.QUEEN,
                suit = Suit.DIAMONDS
            )
        ),
        listOf(
            Card(
                rank = Rank.OTHER,
                suit = Suit.NOTHING
            )
        )
    )

    override val loserCards = listOf(
        listOf(
            Card(
                rank = Rank.ACE,
                suit = Suit.HEARTS
            ),
            Card(
                rank = Rank.ACE,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.ACE,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.ACE,
                suit = Suit.CLUBS
            )
        ),
        listOf(
            Card(
                rank = Rank.KING,
                suit = Suit.HEARTS
            ),
            Card(
                rank = Rank.KING,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.KING,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.KING,
                suit = Suit.CLUBS
            )
        ),
        listOf(
            Card(
                rank = Rank.QUEEN,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.QUEEN,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.QUEEN,
                suit = Suit.CLUBS
            )
        ),
        listOf(
            Card(
                rank = Rank.QUEEN,
                suit = Suit.HEARTS
            )
        ),
        listOf(
            Card(
                rank = Rank.JACK,
                suit = Suit.HEARTS
            ),
            Card(
                rank = Rank.JACK,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.JACK,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.JACK,
                suit = Suit.CLUBS
            )
        ),
        listOf(
            Card(
                rank = Rank.TEN,
                suit = Suit.HEARTS
            ),
            Card(
                rank = Rank.TEN,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.TEN,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.TEN,
                suit = Suit.CLUBS
            )
        ),
        listOf(
            Card(
                rank = Rank.NINE,
                suit = Suit.HEARTS
            ),
            Card(
                rank = Rank.NINE,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.NINE,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.NINE,
                suit = Suit.CLUBS
            )
        ),
        listOf(
            Card(
                rank = Rank.EIGHT,
                suit = Suit.HEARTS
            ),
            Card(
                rank = Rank.EIGHT,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.EIGHT,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.EIGHT,
                suit = Suit.CLUBS
            )
        ),
        listOf(
            Card(
                rank = Rank.SEVEN,
                suit = Suit.HEARTS
            ),
            Card(
                rank = Rank.SEVEN,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.SEVEN,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.SEVEN,
                suit = Suit.CLUBS
            )
        ),
        listOf(
            Card(
                rank = Rank.SIX,
                suit = Suit.HEARTS
            ),
            Card(
                rank = Rank.SIX,
                suit = Suit.DIAMONDS
            ),
            Card(
                rank = Rank.SIX,
                suit = Suit.SPADES
            ),
            Card(
                rank = Rank.SIX,
                suit = Suit.CLUBS
            )
        ),
    )
}