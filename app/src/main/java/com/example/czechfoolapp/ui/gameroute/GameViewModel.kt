package com.example.czechfoolapp.ui.gameroute

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.czechfoolapp.CzechFoolApplication
import com.example.czechfoolapp.data.repository.CurrentGameManager
import com.example.czechfoolapp.ui.gameoptionsroute.GameOptionsViewModel
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.CardChoiceEvent
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.CardUiModel
import com.example.czechfoolapp.ui.gameroute.gameprogressroute.GameProgressEvent
import com.example.czechfoolapp.ui.gameroute.gameprogressroute.GameProgressState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val currentGameManager: CurrentGameManager
) : ViewModel() {
    private val currentGameFlow = currentGameManager.getCurrentGame()
    private val currentWinnerIDFlow = MutableStateFlow<Int?>(null)
    private val currentRoundPlayerScoresFlow = MutableStateFlow(mapOf<Int, Int>())

    val gameProgressState =
        combine(
            currentGameFlow,
            currentWinnerIDFlow,
            currentRoundPlayerScoresFlow
        ) { game, winnerID, currentRoundPlayerScores ->
            GameProgressState(
                players = game.players.map { it.copy(score = it.score + (currentRoundPlayerScores[it.id] ?: 0)) },
                winnerID = winnerID,
                updatedPlayerIDs = currentRoundPlayerScores.filter { it.value != 0 }.keys.toSet()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GameProgressState(emptyList(),null, emptySet())
        )


    private var currentChosenPlayerId: Int? = null
    private val _cardChoiceState = mutableStateListOf<CardUiModel>()
    val cardChoiceState = derivedStateOf { _cardChoiceState.toList() }
    fun onGameProgressEvent(event: GameProgressEvent) {
        when(event) {
            is GameProgressEvent.Cancel -> {
                cancelGame(onNavigateCancel = event.onNavigateCancel)
            }
            is GameProgressEvent.PlayerClicked -> {
                if (currentWinnerIDFlow.value == null) {
                    selectWinner(
                        id = event.id
                    )
                } else {
                    selectPlayer(
                        id = event.id
                    )
                }
            }
            is GameProgressEvent.Done -> {
                updatePlayerScoresAndStartNewRound()
            }
        }
    }

    private fun selectWinner(id: Int) {
        currentWinnerIDFlow.value = id
        selectPlayer(id)
    }

    fun onCardChoiceEvent(event: CardChoiceEvent) {
        when(event) {
            is CardChoiceEvent.CountChanged -> {
                updateCardCount(
                    index = event.index,
                    newCount = event.count
                )
            }
            is CardChoiceEvent.Done -> {
                updatePlayerScore()
            }
        }
    }

    private fun updatePlayerScore() {
        if (currentChosenPlayerId == null) {
            throw IllegalStateException("No player chosen")
        }
        val score = _cardChoiceState.sumOf { it.getScore() }
        val actualScore = if (isWinnerState()) -score else score
        currentRoundPlayerScoresFlow.update { currentState ->
            currentState.toMutableMap().apply {
                this[currentChosenPlayerId!!] = actualScore
            }
        }
    }

    private fun updatePlayerScoresAndStartNewRound() {
        viewModelScope.launch {
            updatePlayerScores()
            startNewRound()
        }
    }

    private fun startNewRound() {
        currentWinnerIDFlow.value = null
        currentRoundPlayerScoresFlow.value = emptyMap()
        currentChosenPlayerId = null
    }

    private suspend fun updatePlayerScores() {
        currentGameFlow.collect { game ->
            game.players.forEach { player ->
                currentGameManager.updatePlayer(
                    player = player
                        .copy(
                            score = player.score + (currentRoundPlayerScoresFlow.value[player.id] ?: 0)
                        )
                )
            }
        }
    }

    private fun selectPlayer(id: Int) {
        currentChosenPlayerId = id
    }

    private fun cancelGame(onNavigateCancel: () -> Unit) {
        onNavigateCancel()
    }


    private fun updateCardCount(index: Int, newCount: Int) {
        if (currentChosenPlayerId == null) {
            throw IllegalStateException("No player chosen")
        }
        val gameUiModel = _cardChoiceState[index]
        if (gameUiModel.suits.size < newCount ) {
            // some pop up or something could be added
            return
        }
        if (isWinnerState()) {
            _cardChoiceState.map {
                it.copy(count = 0)
            }
        }
        _cardChoiceState[index] = gameUiModel.copy(count = newCount)

    }

    private fun isWinnerState() = currentWinnerIDFlow.value == currentChosenPlayerId

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val currentGameManager = application.container.currentGameManager
                GameViewModel(
                    currentGameManager = currentGameManager
                )
            }
        }
    }
}