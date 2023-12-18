package com.example.czechfoolapp.ui.gameshistoryroute

import androidx.lifecycle.ViewModel
import com.example.czechfoolapp.data.repository.CurrentGameManager
import com.example.czechfoolapp.data.repository.GamesRepository

class GamesHistoryViewModel(
    val currentGameManager: CurrentGameManager,
    val gamesRepository: GamesRepository
): ViewModel() {

}