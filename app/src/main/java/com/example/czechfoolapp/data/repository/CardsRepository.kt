package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Card

interface CardsRepository {
    val winnerCards: List<List<Card>>
    val loserCards: List<List<Card>>
}