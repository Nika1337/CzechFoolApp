package com.example.czechfoolapp.ui.routes.gameroute

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val CURRENT_SCREEN = "currentScreen"
private const val CURRENT_WINNER_ID = "currentWinnerID"
private const val CURRENT_CANDIDATE_WINNER_ID = "currentCandidateWinnerID"
private const val CURRENT_CHOSEN_PLAYER_ID = "currentChosenPlayerID"
private const val CURRENT_ROUND_PLAYER_SCORES = "currentRoundPlayerScores"
private const val CARD_CHOICE_STATE = "cardChoiceState"
class GameViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val currentGameManager: CurrentGameManager,
    private val getCardUIModelsUseCase: GetCardUIModelsUseCase
) : ViewModel() {
    val currentScreenFlow=
        savedStateHandle.getStateFlow(
            key = CURRENT_SCREEN,
            GameCurrentScreen.PLAYER_LIST
        )

    private val currentGameFlow = currentGameManager.getCurrentGame()
    private val currentWinnerIDFlow =
        savedStateHandle.getStateFlow<Int?>(
            key = CURRENT_WINNER_ID,
            null
        )
    private val currentRoundPlayerScoresFlow =
        savedStateHandle.getStateFlow(
            key = CURRENT_ROUND_PLAYER_SCORES,
            mapOf<Int,Int>()
        )
    private var currentCandidateWinnerID: Int?
        get() = savedStateHandle[CURRENT_CANDIDATE_WINNER_ID]
        set(value) {
            savedStateHandle[CURRENT_CANDIDATE_WINNER_ID] = value
        }

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


    private var currentChosenPlayerID: Int?
        get() = savedStateHandle[CURRENT_CHOSEN_PLAYER_ID]
        set(value) {
            savedStateHandle[CURRENT_CHOSEN_PLAYER_ID] = value
        }
    val cardChoiceState =
        savedStateHandle.getStateFlow(
            key = CARD_CHOICE_STATE,
            listOf<CardUiModel>()
        )


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
        savedStateHandle[CURRENT_WINNER_ID] = currentCandidateWinnerID
    }

    fun isCandidateState() = currentChosenPlayerID != null && currentCandidateWinnerID == currentChosenPlayerID

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
        savedStateHandle[CURRENT_SCREEN] = GameCurrentScreen.PLAYER_LIST
    }

    private fun updatePlayerScore() {
        if (currentChosenPlayerID == null) {
            throw IllegalStateException("No player chosen")
        }
        val score = cardChoiceState.value.sumOf { it.getScore() }
        val actualScore = if (isCandidateState()) -score else score
        savedStateHandle[CURRENT_ROUND_PLAYER_SCORES] = currentRoundPlayerScoresFlow.value.let { currentState ->
            currentState.toMutableMap().apply {
                this[currentChosenPlayerID!!] = actualScore
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
        savedStateHandle[CURRENT_WINNER_ID] = null
        savedStateHandle[CURRENT_ROUND_PLAYER_SCORES] = emptyMap<Int,Int>()
        currentChosenPlayerID = null
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
        currentChosenPlayerID = id
        setCardChoiceState()
        savedStateHandle[CURRENT_SCREEN] = GameCurrentScreen.CARD_LIST
    }

    private fun setCardChoiceState() {
        val cardUIModels = getCardUIModelsUseCase(isCandidateState())
        resetCardChoiceState()
        savedStateHandle[CARD_CHOICE_STATE] =  cardChoiceState.value.toMutableList().apply { this.addAll(cardUIModels) }.toList()
    }

    private fun resetCardChoiceState() {
        savedStateHandle[CARD_CHOICE_STATE] = cardChoiceState.value.toMutableList().apply { this.clear() }.toList()
    }

    private fun cancelGame(onNavigateCancel: () -> Unit) {
        if (currentGameManager.isGameInProgress().not()) {
            return
        }
        currentGameManager.stopGame()
        onNavigateCancel()
    }


    private fun updateCardCount(index: Int, newCount: Int) {
        if (currentChosenPlayerID == null) {
            throw IllegalStateException("No player chosen")
        }
        val gameUiModel = cardChoiceState.value[index]
        if (gameUiModel.suits.size < newCount ) {
            return
        }
        val newCardChoiceState = cardChoiceState.value.toMutableList()
        if (isCandidateState()) {
            for (i in cardChoiceState.value.indices) {
                newCardChoiceState[i] = cardChoiceState.value[i].copy(count = 0)
            }
        }
        newCardChoiceState[index] = gameUiModel.copy(count = newCount)
        savedStateHandle[CARD_CHOICE_STATE] = newCardChoiceState.toList()
    }



    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = this.createSavedStateHandle()
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val currentGameManager = application.container.currentGameManager
                val getCardUIModelsUseCase = application.container.getCardUIModelsUseCase
                GameViewModel(
                    savedStateHandle = savedStateHandle,
                    currentGameManager = currentGameManager,
                    getCardUIModelsUseCase = getCardUIModelsUseCase
                )
            }
        }
    }
}