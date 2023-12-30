package com.example.czechfoolapp.ui.gameroute.cardchoiceroute

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.czechfoolapp.R
import com.example.czechfoolapp.ui.composables.CzechFoolSmallTopAppBar
import com.example.czechfoolapp.ui.composables.Title
import com.example.czechfoolapp.ui.gameroute.GameEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardChoiceScreen(
    state: List<CardUiModel>,
    onEvent: (event: GameEvent) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null
) {
    if (onNavigateUp != null) {
        BackHandler {
            onNavigateUp()
        }
    }
    Scaffold(
        topBar = {
            CzechFoolSmallTopAppBar(
                onNavigateUp = onNavigateUp,
                title = { Title(stringResource(R.string.choose_cards)) }
            )
        }
    ) { innerPadding ->
        innerPadding
    }
}