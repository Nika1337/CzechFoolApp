package com.example.czechfoolapp.ui.routes.gameoptionsroute.states

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class GameOptionsState(
    val numberOfPlayersState: GameOptionState = GameOptionState(),
    val losingScoreState: GameOptionState = GameOptionState()
) : Parcelable

@Parcelize
@Immutable
data class GameOptionState(
    val value: String = "",
    val errorMessage: String? = null
) : Parcelable
