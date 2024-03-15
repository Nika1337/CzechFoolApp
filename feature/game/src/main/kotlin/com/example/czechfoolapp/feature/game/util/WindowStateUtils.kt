package com.example.czechfoolapp.feature.game.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameContentType : Parcelable {
    PLAYER_LIST_ONLY,
    PLAYER_AND_CARD_LISTS
}

@Parcelize
enum class GameCurrentScreen : Parcelable {
    PLAYER_LIST, CARD_LIST
}