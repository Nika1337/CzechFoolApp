package com.example.czechfoolapp.feature.nameinput.states

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class PlayerNameState(
    val name: String = "",
    val nameError: String? = null
) : Parcelable
