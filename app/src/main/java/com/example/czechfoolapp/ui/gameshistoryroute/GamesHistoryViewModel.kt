package com.example.czechfoolapp.ui.gameshistoryroute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.czechfoolapp.CzechFoolApplication
import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.repository.CurrentGameManager
import com.example.czechfoolapp.data.repository.GamesRepository
import com.example.czechfoolapp.ui.gameshistoryroute.states.GamesHistoryUiState
import com.example.czechfoolapp.ui.gameshistoryroute.util.GamesHistoryCurrentScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class GamesHistoryViewModel(
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


    private var currentChosenGameIdFlow = MutableStateFlow(-1)
    val currentChosenGame: StateFlow<Game?> =
        currentChosenGameIdFlow.flatMapLatest {
            gamesRepository.getGame(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    var currentScreen: GamesHistoryCurrentScreen by mutableStateOf(GamesHistoryCurrentScreen.LIST)
        private set

    fun onEvent(event: GamesHistoryEvent) {
        when(event) {
            is GamesHistoryEvent.ViewGameDetails -> {
                chooseGame(event.gameId)
            }
            is GamesHistoryEvent.ContinueGame -> {
                continueGame(event.gameId)
            }
            is GamesHistoryEvent.StartNewGame -> {
                event.onStartNewGameNavigate()
            }
            is GamesHistoryEvent.DetailScreenNavigateUp -> {
                currentScreen = GamesHistoryCurrentScreen.LIST
            }
        }
    }

    private fun continueGame(gameId: Int) {
        viewModelScope.launch {
            currentGameManager.continueGame(gameId)
        }
    }

    private fun chooseGame(gameId: Int) {
        currentChosenGameIdFlow.value = gameId
        currentScreen = GamesHistoryCurrentScreen.DETAIL
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val currentGameManager = application.container.currentGameManager
                val gamesRepository = application.container.gamesRepository
                GamesHistoryViewModel(
                    currentGameManager = currentGameManager,
                    gamesRepository = gamesRepository
                )
            }
        }
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

