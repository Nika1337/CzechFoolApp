package com.example.czechfoolapp.domain.use_case



private const val MAX_NUMBER_OF_PLAYERS = 16
class ValidateNumberOfPlayersUseCase {
    fun execute(numberOfPlayers: String) : ValidationResult {
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
                    errorMessage = "More than 16 players not supported"
                )
        }
        return ValidationResult(
            successful = true
        )
    }
}

