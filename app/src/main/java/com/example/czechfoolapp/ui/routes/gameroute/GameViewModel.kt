package com.example.czechfoolapp.ui.routes.gameroute

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
import com.example.czechfoolapp.ui.routes.gameroute.cardchoiceroute.CardChoiceEvent
import com.example.czechfoolapp.ui.routes.gameroute.cardchoiceroute.CardUiModel
import com.example.czechfoolapp.ui.routes.gameroute.gameprogressroute.GameProgressEvent
import com.example.czechfoolapp.ui.routes.gameroute.gameprogressroute.GameProgressState
import com.example.czechfoolapp.ui.routes.gameroute.util.GameCurrentScreen
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
    private var currentCandidateWinnerID: Int? = null

    val gameProgressState =
        combine(
            currentGameFlow,
            currentWinnerIDFlow,
            currentRoundPlayerScoresFlow
        ) { game, winnerID, currentRoundPlayerScores ->
            GameProgressState(
                players = game.players.map { it.copy(score = it.score + (currentRoundPlayerScores[it.id] ?: 0)) },
                winnerID = winnerID,
                updatedPlayerIDs = currentRoundPlayerScores.filter { it.value != 0 }.keys.toSet(),
                isGameFinished = game.isFinished
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GameProgressState(emptyList(),null, emptySet(), false)
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
                when (currentWinnerIDFlow.value) {
                    null -> {
                        selectCandidate(
                            id = event.id
                        )
                    }
                    event.id -> {
                        resetRound()
                    }
                    else -> {
                        selectPlayer(
                            id = event.id
                        )
                    }
                }
            }
            is GameProgressEvent.Done -> {
                if (areAllPlayerCardsInputted().not()) {
                    return
                }
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
                if (isAtLeastOneCardSelected().not()) {
                    return
                }
                updatePlayerScore()
                if (isCandidateState()) {
                    selectWinner()
                }
                navigateUp()
                resetCardChoiceState()
            }
            is CardChoiceEvent.NavigateUp -> {
                navigateUp()
            }
        }
    }

    private fun selectWinner() {
        currentWinnerIDFlow.value = currentCandidateWinnerID
    }

    fun isCandidateState() = currentChosenPlayerId != null && currentCandidateWinnerID == currentChosenPlayerId

    private fun areAllPlayerCardsInputted() =
        currentRoundPlayerScoresFlow.value.filter {
            it.key != currentWinnerIDFlow.value
        }.size == gameProgressState.value.players.size - 1


    private fun isAtLeastOneCardSelected() = cardChoiceState.value.any {
        it.count > 0
    }

    private fun selectCandidate(id: Int) {
        currentCandidateWinnerID = id
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
        val actualScore = if (isCandidateState()) -score else score
        currentRoundPlayerScoresFlow.update { currentState ->
            currentState.toMutableMap().apply {
                this[currentChosenPlayerId!!] = actualScore
            }
        }
    }

    private fun updatePlayerScoresAndStartNewRound() {
        viewModelScope.launch {
            updatePlayerScores()
            resetRound()
        }
    }

    private fun resetRound() {
        currentWinnerIDFlow.value = null
        currentRoundPlayerScoresFlow.value = emptyMap()
        currentChosenPlayerId = null
        currentCandidateWinnerID = null
        resetCardChoiceState()
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
        setCardChoiceState()
        currentScreen = GameCurrentScreen.CARD_LIST
    }

    private fun setCardChoiceState() {
        val cardUIModels = getCardUIModelsUseCase(isCandidateState())
        resetCardChoiceState()
        _cardChoiceState.addAll(cardUIModels)
    }

    private fun resetCardChoiceState() {
        _cardChoiceState.clear()
    }

    private fun cancelGame(onNavigateCancel: () -> Unit) {
        if (currentGameManager.isGameInProgress().not()) {
            return
        }
        currentGameManager.stopGame()
        onNavigateCancel()
    }


    private fun updateCardCount(index: Int, newCount: Int) {
        if (currentChosenPlayerId == null) {
            throw IllegalStateException("No player chosen")
        }
        val gameUiModel = _cardChoiceState[index]
        if (gameUiModel.suits.size < newCount ) {
            return
        }
        if (isCandidateState()) {
            for (i in _cardChoiceState.indices) {
                _cardChoiceState[i] = _cardChoiceState[i].copy(count = 0)
            }
        }
        _cardChoiceState[index] = gameUiModel.copy(count = newCount)

    }



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