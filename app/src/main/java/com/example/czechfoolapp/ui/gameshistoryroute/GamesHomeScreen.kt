package com.example.czechfoolapp.ui.gameshistoryroute

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R
import com.example.czechfoolapp.ui.CzechFoolTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesHomeScreen(
    gamesHistoryUiState: GamesHistoryUiState,
    onEvent: (event: GamesHistoryEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
                 CzechFoolTopAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(GamesHistoryEvent.StartNewGame)
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.start_new_game)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        GamesList(
            gamesHistoryUiState = gamesHistoryUiState,
            onGameClicked = {
                onEvent(GamesHistoryEvent.ViewGameDetails(it))
            },
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun GamesList(
    gamesHistoryUiState: GamesHistoryUiState,
    onGameClicked: (gameId: Int) -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
fun GamesHomeScreenPreview() {
    GamesHomeScreen(
        gamesHistoryUiState = GamesHistoryUiState(emptyList()),
        onEvent = {}
    )
}



