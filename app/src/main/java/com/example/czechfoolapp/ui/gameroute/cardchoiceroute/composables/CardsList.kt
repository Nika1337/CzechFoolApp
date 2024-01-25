package com.example.czechfoolapp.ui.gameroute.cardchoiceroute.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R
import com.example.czechfoolapp.data.model.Rank
import com.example.czechfoolapp.data.model.Suit
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.CardUiModel
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.PlusMinusSelectionButtons
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.RadioSelectionButton
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.SelectionButtons


@Composable
fun CardsList(
    state: List<CardUiModel>,
    onCountChange: (index: Int, count: Int) -> Unit,
    selectionButtons: SelectionButtons,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(state) {index, cardUiModel ->
            PlayingCardCard(
                rank = cardUiModel.rank,
                suits = cardUiModel.suits,
                selectionButtons = {
                    selectionButtons(
                        count = cardUiModel.count,
                        onCountChange = { count -> onCountChange(index, count) },
                        modifier = Modifier
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
            )
        }
    }
}

@Preview
@Composable
fun CardsListRadioPreview() {
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
    CardsList(
        state = state,
        onCountChange = { i, j ->  },
        selectionButtons = RadioSelectionButton()
    )
}

@Preview
@Composable
fun CardsListPlusMinusPreview() {
    val state = listOf(
        CardUiModel(
            rank = Rank.QUEEN,
            suits = setOf(Suit.CLUBS, Suit.SPADES, Suit.DIAMONDS),
            count = 1
        ),
        CardUiModel(
            rank = Rank.JACK,
            suits = setOf(Suit.HEARTS, Suit.CLUBS, Suit.DIAMONDS, Suit.SPADES),
            count = 0
        ),
    )
    CardsList(
        state = state,
        onCountChange = { i, j -> },
        selectionButtons = PlusMinusSelectionButtons()
    )
}