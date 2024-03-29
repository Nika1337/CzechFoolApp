package com.example.czechfoolapp.feature.gameshistory

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.czechfoolapp.core.designsystem.component.Branding
import com.example.czechfoolapp.core.designsystem.component.CzechFoolFloatingActionButton
import com.example.czechfoolapp.core.designsystem.component.CzechFoolTopAppBar
import com.example.czechfoolapp.core.designsystem.theme.CzechFoolAppTheme
import com.example.czechfoolapp.core.model.Game
import com.example.czechfoolapp.core.model.Player
import com.example.czechfoolapp.feature.gameshistory.composables.Date
import com.example.czechfoolapp.feature.gameshistory.composables.LosingScore
import com.example.czechfoolapp.feature.gameshistory.composables.PlayerAndScore
import com.example.czechfoolapp.feature.gameshistory.states.GamesHistoryUiState
import java.time.LocalDateTime
import java.time.Month

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesHistoryScreen(
    gamesHistoryUiState: GamesHistoryUiState,
    onEvent: (event: GamesHistoryEvent) -> Unit,
    onNavigateStartNewGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
                 CzechFoolTopAppBar(
                     scrollBehavior = scrollBehavior,
                     title = {
                         Branding(modifier = Modifier.height(dimensionResource(R.dimen.logo_height)))
                     }
                 )
        },
        floatingActionButton = {
            CzechFoolFloatingActionButton(
                onClick = {
                    onEvent(
                        GamesHistoryEvent.StartNewGame(
                            onStartNewGameNavigate = onNavigateStartNewGame
                        )
                    )
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.start_new_game)
                )
            }
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
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
                game = it,
                onGameClicked = onGameClicked,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_small)
                    )
            )
        }
        item {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_large)))
        }
    }
}

@Composable
fun GameCard(
    game: Game,
    onGameClicked: (gameId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable {
                onGameClicked(game.id)
            }
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_small))
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            TitleAndDate(
                title = stringResource(R.string.game_title, game.id),
                date = game.date,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            PlayerNamesAndScores(
                players = game.players,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            LosingScoreAndGameStatus(
                losingScore = game.losingScore,
                status = if (game.isFinished) {
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
        LosingScore(
            score = losingScore,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = status,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun PlayerNamesAndScores(
    players: List<Player>,
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
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 2.dp),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
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
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Date(
            dateTime = date,
            formatPattern = "MMMM d\t\tHH:mm",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.6f)
        )
    }
}






@Preview
@Composable
fun GamesHomeScreenPreview() {
    CzechFoolAppTheme(darkTheme = true) {
        GamesHistoryScreen(
            gamesHistoryUiState = GamesHistoryUiState(
                listOf(
                    Game(
                        id = 134,
                        losingScore = 200,
                        date = LocalDateTime.now(),
                        players = listOf(
                            Player(
                                id = 0,
                                name = "Niku",
                                score = 115
                            ),
                            Player(
                                id = 0,
                                name = "Anastasia",
                                score = 64
                            )
                        )
                    ),
                    Game(
                        id = 124,
                        losingScore = 300,
                        date = LocalDateTime.now(),
                        players = listOf(
                            Player(
                                id = 0,
                                name = "Niku",
                                score = 115
                            ),
                            Player(
                                id = 0,
                                name = "Anastasia",
                                score = 64
                            ),
                            Player(
                                id = 0,
                                name = "Neka",
                                score = 198
                            )
                        )
                    ),
                    Game(
                        id = 104,
                        losingScore = 200,
                        date = LocalDateTime.now(),
                        players = listOf(
                            Player(
                                id = 0,
                                name = "Niku",
                                score = 115
                            ),
                            Player(
                                id = 0,
                                name = "Anastasia",
                                score = 64
                            ),
                            Player(
                                id = 0,
                                name = "Neka",
                                score = 3
                            ),
                            Player(
                                id = 0,
                                name = "Wiko",
                                score = 89
                            )
                        )
                    ),
                    Game(
                        id = 104,
                        losingScore = 200,
                        date = LocalDateTime.now(),
                        players = listOf(
                            Player(
                                id = 0,
                                name = "Niku",
                                score = 115
                            ),
                            Player(
                                id = 0,
                                name = "Anastasia",
                                score = 64
                            ),
                            Player(
                                id = 0,
                                name = "Neka",
                                score = 3
                            ),
                            Player(
                                id = 0,
                                name = "Wiko",
                                score = 89
                            )
                        )
                    )
                )
            ),
            onEvent = {},
            onNavigateStartNewGame = {}
        )
    }
}

@Preview
@Composable
fun GameCardPreview() {
    CzechFoolAppTheme(darkTheme = true) {
        GameCard(
            game = Game(
                id = 154,
                losingScore = 200,
                date = LocalDateTime.of(200, Month.NOVEMBER, 9, 17, 54),
                players = listOf(
                    Player(
                        id = 0,
                        name = "Niku",
                        score = 115
                    ),
                    Player(
                        id = 0,
                        name = "Anastasia",
                        score = 64
                    ),
                    Player(
                        id = 0,
                        name = "Neka",
                        score = 203
                    ),
                    Player(
                        id = 0,
                        name = "Wiko",
                        score = 89
                    )
                )
            ),
            onGameClicked = {}
        )
    }
}



