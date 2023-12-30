package com.example.czechfoolapp.ui.gameroute.gameprogressroute

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.ui.composables.CzechFoolSmallTopAppBar
import com.example.czechfoolapp.ui.composables.Title
import com.example.czechfoolapp.ui.gameroute.GameEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameProgressScreen(
    gameProgressState: GameProgressState,
    onNavigateCancel: () -> Unit,
    onEvent: (event: GameEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CzechFoolSmallTopAppBar(
                title = { Title(gameProgressState.nextUserStep.title) },
                onNavigateUp = {
                    onEvent(GameEvent.Cancel(onNavigateCancel))
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        PlayersList(
            gameProgressState = gameProgressState,
            onPlayerClicked = { onEvent(GameEvent.PlayerClicked(it)) },
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun GameProgressScreenPreview() {
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
        winnerID = 2,
        updatedPlayerIDs = setOf(1,3)
    )
    GameProgressScreen(
        gameProgressState = gameProgressState,
        onNavigateCancel = {},
        onEvent = {}
    )
}



