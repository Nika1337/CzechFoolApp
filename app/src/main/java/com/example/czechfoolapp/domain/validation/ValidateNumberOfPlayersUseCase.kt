package com.example.czechfoolapp.domain.validation

import javax.inject.Inject


private const val MAX_NUMBER_OF_PLAYERS = 16
class ValidateNumberOfPlayersUseCase @Inject constructor() {
    operator fun invoke(numberOfPlayers: String) : ValidationResult {
        if (numberOfPlayers.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Number of players can't be blank"
            )
        }
        if (numberOfPlayers.toIntOrNull() == null) {
            return ValidationResult(
                successful = false,
                errorMessage = "Number of players should be an integer"
            )
        }
        if (numberOfPlayers.toInt() > MAX_NUMBER_OF_PLAYERS) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "More than $MAX_NUMBER_OF_PLAYERS players not supported"
                )
        }
        return ValidationResult(
            successful = true
        )
    }
}

