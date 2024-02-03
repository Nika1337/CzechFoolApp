package com.example.czechfoolapp.ui.routes.gameroute.cardchoiceroute.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R
import com.example.czechfoolapp.data.model.Rank
import com.example.czechfoolapp.data.model.Suit
import com.example.czechfoolapp.ui.routes.gameroute.cardchoiceroute.PlusMinusSelectionButtons
import com.example.czechfoolapp.ui.routes.gameroute.cardchoiceroute.RadioSelectionButton

@Composable
fun PlayingCardCard(
    rank: Rank,
    suits: Set<Suit>,
    modifier: Modifier = Modifier,
    selectionButtons: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_small))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Symbol(
                    symbol = rank.symbol
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                Suits(
                    suits = suits
                )
            }
            Spacer(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.padding_small))
            )
            selectionButtons()
        }
    }
}

@Composable
fun Suits(
    suits: Set<Suit>,
    modifier: Modifier = Modifier
) {
    val suitsStringBuilder = StringBuilder("")
    suits.forEach {
        suitsStringBuilder.append(" " + it.symbol)
    }
    Text(
        text = suitsStringBuilder.toString(),
        style = MaterialTheme.typography.headlineMedium,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        maxLines = 1
    )
//    Row(
//        modifier = modifier,
//    ) {
//        suits.forEach {
//            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_mini)))
//            Text(
//                text = it.symbol,
//                style = MaterialTheme.typography.headlineMedium
//            )
//        }
//    }
}

@Composable
fun Symbol(
    symbol: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = symbol,
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}


@Preview()
@Composable
fun PlayingCardCardPreview() {
    PlayingCardCard(
        rank = Rank.JACK,
        suits = setOf(Suit.HEARTS, Suit.CLUBS, Suit.SPADES, Suit.DIAMONDS),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        PlusMinusSelectionButtons()(
            count = 1,
            onCountChange = {},
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun PlayingCardCardRadioPreview() {
    PlayingCardCard(
        rank = Rank.JACK,
        suits = setOf(Suit.HEARTS, Suit.CLUBS, Suit.SPADES, Suit.DIAMONDS),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Box(
            Modifier
        ) {
            RadioSelectionButton()(
                count = 1,
                onCountChange = {},
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}
