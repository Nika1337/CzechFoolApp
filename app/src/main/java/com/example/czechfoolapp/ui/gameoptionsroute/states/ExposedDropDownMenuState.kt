package com.example.czechfoolapp.ui.gameoptionsroute.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue


open class ExposedDropDownMenuState(
     validator: (String) -> Boolean = { true },
     errorFor: (String) -> String = { "" },
) : TextFieldState(validator = validator, errorFor = errorFor) {
    var expanded: Boolean by mutableStateOf(false)
}
fun exposedDropDownMenuStateSaver(state: ExposedDropDownMenuState) = listSaver(
    save = { listOf(it.text, it.expanded) },
    restore = {
        state.apply {
            text = it[0] as String
            expanded = it[1] as Boolean
        }
    }
)