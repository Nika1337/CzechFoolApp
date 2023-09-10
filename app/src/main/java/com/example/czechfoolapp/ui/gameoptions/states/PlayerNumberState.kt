package com.example.czechfoolapp.ui.gameoptions.states

import com.example.czechfoolapp.data.DefaultValuesSource
import java.util.regex.Pattern

private const val MAX_NUMBER_OF_PLAYERS = 16
private const val SCORE_VALIDATION_REGEX = "^(\\d)+\$"

class PlayerNumberState :
    ExposedDropDownMenuState(validator = ::isPlayerNumberValid, errorFor = ::playerNumberValidationError) {
        init {
            text = DefaultValuesSource.defaultNumberOfPlayers
        }
    }

private fun isNumber(score: String): Boolean {
    return Pattern.matches(SCORE_VALIDATION_REGEX, score)
}

private fun playerNumberValidationError(score: String): String {
    return if (!isNumber(score)) {
        "Number of players should be a positive integer"
    } else {
        "More than $MAX_NUMBER_OF_PLAYERS not supported"
    }
}

private fun isPlayerNumberValid(score: String): Boolean {
    return isNumber(score) && (score.toInt() <= MAX_NUMBER_OF_PLAYERS)
}

val PlayerNumberStateSaver = exposedDropDownMenuStateSaver(PlayerNumberState())