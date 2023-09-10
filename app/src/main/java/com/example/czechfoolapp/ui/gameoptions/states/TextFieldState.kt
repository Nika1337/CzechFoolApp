package com.example.czechfoolapp.ui.gameoptions.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorFor: (String) -> String = { "" }
) {
    var text: String by mutableStateOf("")
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)


    fun enableShowErrors() {
        displayErrors = true
    }
    fun showErrors() = !isValid && displayErrors


    open fun getError(): String? {
        return if (showErrors()) {
            errorFor(text)
        } else {
            null
        }
    }


}

fun textFieldStateSaver(state: TextFieldState) = listSaver(
    save = { listOf(it.text) },
    restore = {
        state.apply {
            text = it[0] as String
        }
    }
)