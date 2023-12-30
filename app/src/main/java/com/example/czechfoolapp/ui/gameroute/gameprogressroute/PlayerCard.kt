package com.example.czechfoolapp.ui.gameroute.gameprogressroute

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R
import com.example.czechfoolapp.data.model.Player


@Composable
fun PlayerCard(
    player: Player,
    onPlayerClicked: (id: Int) -> Unit,
    isWinner: Boolean,
    isUpdated: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        if (isWinner) MaterialTheme.colorScheme.inversePrimary
        else if (isUpdated) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.surfaceVariant,
        label = "color"
    )
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_small)),
        colors = CardDefaults.cardColors(
            containerColor = animatedColor,
        )
    ) {
        PlayerRow(
            player = player,
            onPlayerClicked = onPlayerClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
fun PlayerRow(
    player: Player,
    modifier: Modifier = Modifier,
    onPlayerClicked: (id: Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = player.id.toString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small)),
                maxLines = 1
            )
            Text(
                text = player.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_small)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Score(
                score = player.score.toString(),
                modifier = Modifier
                    .height(dimensionResource(R.dimen.score_height))
                    .width(dimensionResource(R.dimen.score_width))
                    .background(
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        shape = MaterialTheme.shapes.extraLarge
                    )
            )
            PlayerButton(
                onPlayerClicked = { onPlayerClicked(player.id) },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PlayerButton(
    onPlayerClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onPlayerClicked,
        modifier = modifier
    ) {
       Icon(
           imageVector = Icons.Default.KeyboardArrowRight,
           contentDescription = null
       )
    }
}

@Composable
fun Score(
    score: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Text(
            text = score,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1
        )
    }
}


@Preview
@Composable
fun PlayerCardPreview() {
    val player = Player(
        id = 1,
        name = "Anastasia",
        score = 198
    )
    Column {
        PlayerCard(
            player = player,
            onPlayerClicked = {},
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            isWinner = false,
            isUpdated = true
        )
        PlayerCard(
            player = player,
            onPlayerClicked = {},
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            isWinner = true,
            isUpdated = false
        )
    }
}