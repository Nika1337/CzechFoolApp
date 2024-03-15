package com.example.czechfoolapp.feature.game.gameprogressroute

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.core.designsystem.component.CzechFoolFloatingActionButton
import com.example.czechfoolapp.core.designsystem.component.CzechFoolTopAppBar
import com.example.czechfoolapp.core.designsystem.component.Title
import com.example.czechfoolapp.core.model.Player
import com.example.czechfoolapp.feature.game.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameProgressScreen(
    gameProgressState: GameProgressState,
    onNavigateCancel: () -> Unit,
    onEvent: (event: GameProgressEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    if (gameProgressState.isGameFinished) {
        onEvent(GameProgressEvent.Cancel(onNavigateCancel))
    }

    Scaffold(
        topBar = {
            CzechFoolTopAppBar(
                title = { Title(gameProgressState.nextUserStep.title) },
                onNavigateUp = {
                    onEvent(GameProgressEvent.Cancel(onNavigateCancel))
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.cancel_game)
                    )
                }
            )
        },
        floatingActionButton = {
            CzechFoolFloatingActionButton(
                onClick = { onEvent(GameProgressEvent.Done) },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.done)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        PlayersList(
            gameProgressState = gameProgressState,
            onPlayerClicked = { onEvent(GameProgressEvent.PlayerClicked(it)) },
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
        updatedPlayerIDs = setOf(1,3),
        isGameFinished = false
    )
    GameProgressScreen(
        gameProgressState = gameProgressState,
        onNavigateCancel = {},
        onEvent = {}
    )
}



