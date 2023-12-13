package com.example.czechfoolapp.ui.gameshistoryroute

import androidx.lifecycle.ViewModel
import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.GamesRepository

class GamesHistoryViewModel(
    val currentGameRepository: CurrentGameRepository,
    val gamesRepository: GamesRepository
): ViewModel() {

}