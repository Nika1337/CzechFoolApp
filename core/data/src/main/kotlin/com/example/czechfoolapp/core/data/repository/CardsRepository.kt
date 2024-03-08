package com.example.czechfoolapp.core.data.repository

import com.example.czechfoolapp.core.model.Card


interface CardsRepository {
    val winnerCards: List<List<Card>>
    val loserCards: List<List<Card>>
}