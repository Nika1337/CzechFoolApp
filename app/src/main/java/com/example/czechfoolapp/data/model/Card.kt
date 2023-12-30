package com.example.czechfoolapp.data.model

enum class Suit(
    val symbol: String
) {
    CLUBS(
        symbol = "♣"
    ),
    DIAMONDS(
        symbol = "♦"
    ),
    HEARTS(
        symbol = "♥"
    ),
    SPADES(
        symbol = "♠"
    )
}

enum class Rank(
    val point: Int,
    val symbol: String
) {
    SIX(
        point = 6,
        symbol = "6"
    ),
    SEVEN(
        point = 7,
        symbol = "7"
    ),
    EIGHT(
        point = 8,
        symbol = "8"
    ),
    NINE(
        point = 9,
        symbol = "9"
    ),
    TEN(
        point = 10,
        symbol = "10"
    ),
    JACK(
        point = 2,
        symbol = "J"
    ),
    QUEEN(
        point = 20,
        symbol = "Q"
    ),
    KING(
        point = 4,
        symbol = "K"
    ),
    ACE(
        point = 11,
        symbol = "A"
    )
}

data class Card(
    val rank: Rank,
    val suit: Suit,
) {
    val canBeFinishedWith = rank != Rank.SIX && rank != Rank.ACE
    val point = if (rank == Rank.QUEEN && suit == Suit.HEARTS) 40 else rank.point
}