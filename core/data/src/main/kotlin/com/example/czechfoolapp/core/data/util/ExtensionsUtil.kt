package com.example.czechfoolapp.core.data.util

import java.util.Locale

fun String.capitalizeFirstCharacter(): String {
    if (this.isEmpty()) {
        return this
    }
    return this.substring(0, 1).uppercase(Locale.ROOT) + this.substring(1).lowercase(Locale.ROOT)
}
