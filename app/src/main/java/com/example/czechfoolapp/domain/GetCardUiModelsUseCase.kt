package com.example.czechfoolapp.domain

import com.example.czechfoolapp.data.repository.CardsRepository
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.CardUiModel
import com.example.czechfoolapp.util.getSuits

class GetCardUIModelsUseCase(
    private val cardsRepository: CardsRepository
) {
    operator fun invoke(
        isWinner: Boolean
    ) : List<CardUiModel>  {
        return if (isWinner) {
            getWinnerCardUIModels()
        } else {
            getLoserCardUIModels()
        }
    }

    private fun getWinnerCardUIModels() = cardsRepository.winnerCards.map {
        CardUiModel(
            rank = it[0].rank,
            suits = it.getSuits()
        )
    }

    private fun getLoserCardUIModels() = cardsRepository.loserCards.map {
        CardUiModel(
            rank = it[0].rank,
            suits = it.getSuits()
        )
    }
}