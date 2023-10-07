package com.example.czechfoolapp.ui.gameoptionsroute.states

import com.example.czechfoolapp.data.DefaultValuesSource
import java.util.regex.Pattern


private const val MAX_SCORE = 1000
private const val SCORE_VALIDATION_REGEX = "^(\\d)+\$"

class ScoreState :
    ExposedDropDownMenuState(validator = ::isScoreValid, errorFor = ::scoreValidationError) {
        init {
            text = DefaultValuesSource.defaultScore.text
        }
    }

private fun isNumber(score: String): Boolean {
    return Pattern.matches(SCORE_VALIDATION_REGEX, score)
}

fun scoreValidationError(score: String): String {
    return if (!isNumber(score)) {
        "Score should be a positive integer"
    } else {
        "Score can't be more than $MAX_SCORE"
    }
}

private fun isScoreValid(score: String): Boolean {
    return isNumber(score) && (score.toInt() <= MAX_SCORE)
}

val ScoreStateSaver = exposedDropDownMenuStateSaver(ScoreState())
