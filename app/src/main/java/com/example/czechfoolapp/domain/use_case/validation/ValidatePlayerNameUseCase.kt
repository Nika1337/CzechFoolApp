package com.example.czechfoolapp.domain.use_case.validation


private const val MAX_LENGTH_OF_NAME = 16
class ValidatePlayerNameUseCase {
    operator fun invoke(name: String) : ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't be blank"
            )
        }
        if (name.length > MAX_LENGTH_OF_NAME) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't be longer than $MAX_LENGTH_OF_NAME"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}