package com.example.czechfoolapp.ui.routes.gameroute.gameprogressroute

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R
import com.example.czechfoolapp.data.model.Player

@Composable
fun PlayersList(
    gameProgressState: GameProgressState,
    onPlayerClicked: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(gameProgressState.players) {
            PlayerCard(
                player = it,
                isWinner = it.id == gameProgressState.winnerID,
                isUpdated = gameProgressState.updatedPlayerIDs.contains(it.id),
                onPlayerClicked = onPlayerClicked,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_small)
                    )
            )
        }
    }
}

@Preview
@Composable
fun PlayersListPreview() {
    val players = listOf(
        Player(
            id = 1,
            name = "Taso",
            score = 198
        ),
        Player(
            id = 2,
            name = "Wiko",
            score = 56
        ),
        Player(
            id = 3,
            name = "Neka",
            score = 109
        ),
        Player(
            id = 4,
            name = "Nika"
        )
    )
    val gameProgressState = GameProgressState(
        players = players,
        updatedPlayerIDs = emptySet(),
        winnerID = null,
        isGameFinished = false
    )
    PlayersList(
        gameProgressState = gameProgressState,
        onPlayerClicked = {}
    )
}