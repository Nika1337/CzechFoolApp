package com.example.czechfoolapp.ui.gameoptionsroute.states

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameOptionsState(
    val numberOfPlayersState: GameOptionState = GameOptionState(),
    val losingScoreState: GameOptionState = GameOptionState()
) : Parcelable

@Parcelize
data class GameOptionState(
    val value: String = "",
    val errorMessage: String? = null
) : Parcelable
