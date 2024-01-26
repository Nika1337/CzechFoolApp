package com.example.czechfoolapp.ui.gameroute

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.czechfoolapp.CzechFoolApplication
import com.example.czechfoolapp.data.repository.CurrentGameManager
import com.example.czechfoolapp.domain.GetCardUIModelsUseCase
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.CardChoiceEvent
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.CardUiModel
import com.example.czechfoolapp.ui.gameroute.gameprogressroute.GameProgressEvent
import com.example.czechfoolapp.ui.gameroute.gameprogressroute.GameProgressState
import com.example.czechfoolapp.ui.gameroute.util.GameCurrentScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val currentGameManager: CurrentGameManager,
    private val getCardUIModelsUseCase: GetCardUIModelsUseCase
) : ViewModel() {
    var currentScreen: GameCurrentScreen by mutableStateOf(GameCurrentScreen.PLAYER_LIST)
        private set

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
                navigateUp()
            }
            is CardChoiceEvent.NavigateUp -> {
                navigateUp()
            }
        }
    }

    private fun selectWinner(id: Int) {
        currentWinnerIDFlow.value = id
        selectPlayer(id)
    }

    private fun navigateUp() {
        currentScreen = GameCurrentScreen.PLAYER_LIST
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
        currentGameFlow.first().let { game ->
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
        resetCardChoiceState()
        currentScreen = GameCurrentScreen.CARD_LIST
    }

    private fun resetCardChoiceState() {
        val cardUIModels = getCardUIModelsUseCase(isWinnerState())
        _cardChoiceState.clear()
        _cardChoiceState.addAll(cardUIModels)
    }

    private fun cancelGame(onNavigateCancel: () -> Unit) {
        currentGameManager.stopGame()
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
            for (i in _cardChoiceState.indices) {
                _cardChoiceState[i] = _cardChoiceState[i].copy(count = 0)
            }
        }
        _cardChoiceState[index] = gameUiModel.copy(count = newCount)

    }

    fun isWinnerState() = currentChosenPlayerId != null && currentWinnerIDFlow.value == currentChosenPlayerId

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val currentGameManager = application.container.currentGameManager
                val getCardUIModelsUseCase = application.container.getCardUIModelsUseCase
                GameViewModel(
                    currentGameManager = currentGameManager,
                    getCardUIModelsUseCase = getCardUIModelsUseCase
                )
            }
        }
    }
}