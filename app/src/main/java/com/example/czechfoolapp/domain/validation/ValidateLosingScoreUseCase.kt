package com.example.czechfoolapp.domain.validation

import javax.inject.Inject


private const val MAX_LOSING_SCORE = 1000

class ValidateLosingScoreUseCase @Inject constructor() {
    operator fun invoke(score: String) : ValidationResult {
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
                errorMessage = "More than $MAX_LOSING_SCORE losing score not supported"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}