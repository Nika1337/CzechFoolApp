package com.example.czechfoolapp.ui.gameoptions.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


open class ExposedDropDownMenuState(
    private val validator: (String) -> Boolean = { true },
    private val errorFor: (String) -> String = { "" },
) : TextFieldState(validator = validator, errorFor = errorFor) {
    var extended: Boolean by mutableStateOf(false)
}