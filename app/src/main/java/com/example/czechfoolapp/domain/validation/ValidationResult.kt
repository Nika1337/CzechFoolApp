package com.example.czechfoolapp.domain.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)