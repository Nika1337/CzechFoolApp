package com.example.czechfoolapp.feature.gameshistory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.czechfoolapp.core.data.repository.CurrentGameManager
import com.example.czechfoolapp.core.data.repository.GamesRepository
import com.example.czechfoolapp.core.model.Game
import com.example.czechfoolapp.feature.gameshistory.states.GamesHistoryUiState
import com.example.czechfoolapp.feature.gameshistory.util.GamesHistoryCurrentScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val CURRENT_CHOSEN_GAME_ID = "currentChosenGameID"
private const val CURRENT_SCREEN = "currentScreen"
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GamesHistoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val currentGameManager: CurrentGameManager,
    private val gamesRepository: GamesRepository
): ViewModel() {
    val gamesHistoryUiState: StateFlow<GamesHistoryUiState> =
        gamesRepository.getAllGames().map { GamesHistoryUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GamesHistoryUiState(emptyList())
            )


    private val currentChosenGameIDFlow =
        savedStateHandle.getStateFlow(
            key = CURRENT_CHOSEN_GAME_ID,
            -1
        )
    val currentChosenGame: StateFlow<Game?> =
        currentChosenGameIDFlow.flatMapLatest {
            gamesRepository.getGame(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    val currentScreen =
        savedStateHandle.getStateFlow(
            key = CURRENT_SCREEN,
            GamesHistoryCurrentScreen.LIST
        )
    fun onEvent(event: GamesHistoryEvent) {
        when(event) {
            is GamesHistoryEvent.ViewGameDetails -> {
                chooseGame(event.gameId)
            }
            is GamesHistoryEvent.ContinueGame -> {
                continueGame(
                    gameId = event.gameId,
                    onNavigateContinue = event.onContinueGameNavigate
                )
            }
            is GamesHistoryEvent.StartNewGame -> {
                event.onStartNewGameNavigate()
            }
            is GamesHistoryEvent.DetailScreenNavigateUp -> {
                savedStateHandle[CURRENT_SCREEN] = GamesHistoryCurrentScreen.LIST
            }
        }
    }

    private fun continueGame(gameId: Int, onNavigateContinue: () -> Unit) {
        if (currentGameManager.isGameInProgress()) {
            return
        }
        viewModelScope.launch {
            currentGameManager.continueGame(gameId)
            onNavigateContinue()
        }
    }

    private fun chooseGame(gameID: Int) {
        savedStateHandle[CURRENT_CHOSEN_GAME_ID] = gameID
        savedStateHandle[CURRENT_SCREEN] = GamesHistoryCurrentScreen.DETAIL
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

