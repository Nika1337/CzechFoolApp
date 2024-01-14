package com.example.czechfoolapp.ui.gameroute.cardchoiceroute

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R
import com.example.czechfoolapp.data.model.Rank
import com.example.czechfoolapp.data.model.Suit
import com.example.czechfoolapp.ui.composables.CzechFoolSmallTopAppBar
import com.example.czechfoolapp.ui.composables.Title
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.composables.CardsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardChoiceScreen(
    state: List<CardUiModel>,
    isWinnerScreen: Boolean,
    onEvent: (event: CardChoiceEvent) -> Unit,
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
        },
        modifier = modifier
    ) { innerPadding ->
        val selectionButtons = if (isWinnerScreen) RadioSelectionButton() else PlusMinusSelectionButtons()
        CardsList(
            state = state,
            onCountChange = { onEvent(CardChoiceEvent.CountChangeClicked(it))} ,
            selectionButtons = selectionButtons,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun CardChoiceWinnerScreenPreview() {
    val state = listOf(
        CardUiModel(
            rank = Rank.QUEEN,
            suits = setOf(Suit.CLUBS, Suit.SPADES, Suit.DIAMONDS),
            count = 1
        ),
        CardUiModel(
            rank = Rank.QUEEN,
            suits = setOf(Suit.HEARTS),
            count = 0
        ),
    )
    CardChoiceScreen(
        state = state,
        isWinnerScreen = true,
        onEvent = {}
    )
}

@Preview
@Composable
fun CardChoiceLoserScreenPreview() {
    val state = listOf(
        CardUiModel(
            rank = Rank.QUEEN,
            suits = setOf(Suit.CLUBS, Suit.SPADES, Suit.DIAMONDS),
            count = 1
        ),
        CardUiModel(
            rank = Rank.JACK,
            suits = setOf(Suit.HEARTS, Suit.CLUBS, Suit.DIAMONDS, Suit.SPADES),
            count = 3
        ),
    )
    CardChoiceScreen(
        state = state,
        isWinnerScreen = false,
        onEvent = {}
    )
}