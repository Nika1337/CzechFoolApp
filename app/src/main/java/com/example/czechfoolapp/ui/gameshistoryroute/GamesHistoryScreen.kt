package com.example.czechfoolapp.ui.gameshistoryroute

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.czechfoolapp.R
import com.example.czechfoolapp.ui.composables.CzechFoolSmallTopAppBar
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesHistoryScreen(
    gamesHistoryUiState: GamesHistoryUiState,
    onEvent: (event: GamesHistoryEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
                 CzechFoolSmallTopAppBar()
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
    LazyColumn(
        modifier = modifier
    ) {
        items(gamesHistoryUiState.games) {
            GameCard(
                gameUiModel = it,
                onGameClicked = onGameClicked,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun GameCard(
    gameUiModel: GameUiModel,
    onGameClicked: (gameId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable {
                onGameClicked(gameUiModel.gameId)
            }
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_small))
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            TitleAndDate(
                title = stringResource(R.string.game_title, gameUiModel.gameId),
                date = gameUiModel.date,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            PlayerNamesAndScores(
                players = gameUiModel.players,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            LosingScoreAndGameStatus(
                losingScore = gameUiModel.losingScore,
                status = if (gameUiModel.isFinished) {
                    stringResource(R.string.finished)
                } else {
                    stringResource(R.string.game_in_progress)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.6f)
            )
        }
    }
}

@Composable
fun LosingScoreAndGameStatus(
    losingScore: Int,
    status: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Losing Score: $losingScore",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = status,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PlayerNamesAndScores(
    players: List<PlayerUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        PlayerAndScore(
            player = "Player",
            score = "Score",
            style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.fillMaxWidth()
        )
        Divider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .padding(vertical = 2.dp)
        )
        players.forEach {
            PlayerAndScore(
                player = it.name,
                score = it.score.toString(),
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.8f)
            )
        }
    }
}

@Composable
private fun PlayerAndScore(
    player: String,
    score: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = player,
            style = style
        )
        Text(
            text = score,
            style = style
        )
    }
}

@Composable
fun TitleAndDate(
    title: String,
    date: LocalDateTime,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
                .copy(
                    fontWeight = FontWeight.Bold
                )
        )
        Text(
            text = date.format(DateTimeFormatter.ofPattern("MMMM d\t\tHH:mm", Locale.ENGLISH)),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.6f)
        )
    }
}






@Preview
@Composable
fun GamesHomeScreenPreview() {
    GamesHistoryScreen(
        gamesHistoryUiState = GamesHistoryUiState(
            listOf(
                GameUiModel(
                    gameId = 134,
                    losingScore = 200,
                    date = LocalDateTime.now(),
                    isFinished = false,
                    players = listOf(
                        PlayerUiModel(
                            name = "Niku",
                            score = 115
                        ),
                        PlayerUiModel(
                            name = "Anastasia",
                            score = 64
                        )
                    )
                ),GameUiModel(
                    gameId = 124,
                    losingScore = 300,
                    date = LocalDateTime.now(),
                    isFinished = false,
                    players = listOf(
                        PlayerUiModel(
                            name = "Niku",
                            score = 115
                        ),
                        PlayerUiModel(
                            name = "Anastasia",
                            score = 64
                        ),
                        PlayerUiModel(
                            name = "Neka",
                            score = 198
                        )
                    )
                ),GameUiModel(
                    gameId = 104,
                    losingScore = 200,
                    date = LocalDateTime.now(),
                    isFinished = false,
                    players = listOf(
                        PlayerUiModel(
                            name = "Niku",
                            score = 115
                        ),
                        PlayerUiModel(
                            name = "Anastasia",
                            score = 64
                        ),
                        PlayerUiModel(
                            name = "Neka",
                            score = 3
                        ),
                        PlayerUiModel(
                            name = "Wiko",
                            score = 89
                        )
                    )
                ),GameUiModel(
                    gameId = 104,
                    losingScore = 200,
                    date = LocalDateTime.now(),
                    isFinished = false,
                    players = listOf(
                        PlayerUiModel(
                            name = "Niku",
                            score = 115
                        ),
                        PlayerUiModel(
                            name = "Anastasia",
                            score = 64
                        ),
                        PlayerUiModel(
                            name = "Neka",
                            score = 3
                        ),
                        PlayerUiModel(
                            name = "Wiko",
                            score = 89
                        )
                    )
                )
            )
        ),
        onEvent = {}
    )
}

@Preview
@Composable
fun GameCardPreview() {
    GameCard(
        gameUiModel = GameUiModel(
            gameId = 154,
            losingScore = 200,
            date = LocalDateTime.of(200, Month.NOVEMBER, 9, 17, 54),
            isFinished = true,
            players = listOf(
                PlayerUiModel(
                    name = "Niku",
                    score = 115
                ),
                PlayerUiModel(
                    name = "Anastasia",
                    score = 64
                ),
                PlayerUiModel(
                    name = "Neka",
                    score = 203
                ),
                PlayerUiModel(
                    name = "Wiko",
                    score = 89
                )
            )
        ),
        onGameClicked = {}
    )
}



