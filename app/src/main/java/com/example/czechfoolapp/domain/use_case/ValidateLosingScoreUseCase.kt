package com.example.czechfoolapp.domain.use_case


private const val MAX_LOSING_SCORE = 1000

class ValidateLosingScoreUseCase {
    fun execute(score: String) : ValidationResult {
        if (score.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Losing score can't be blank"
            )
        }
        if (score.toIntOrNull() == null) {
            return ValidationResult(
                successful = false,
                errorMessage = "Losing Score should be an integer"
            )
        }
        if (score.toInt() > MAX_LOSING_SCORE) {
            return ValidationResult(
                successful = false,
                errorMessage = "More than 1000 losing score not supported"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}