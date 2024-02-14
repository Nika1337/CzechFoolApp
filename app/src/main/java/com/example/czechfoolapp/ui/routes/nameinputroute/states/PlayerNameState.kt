package com.example.czechfoolapp.ui.routes.nameinputroute.states

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class PlayerNameState(
    val name: String = "",
    val nameError: String? = null
) : Parcelable
