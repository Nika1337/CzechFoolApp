package com.example.czechfoolapp.ui.gameshistoryroute

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R
import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.ui.composables.CzechFoolSmallTopAppBar
import com.example.czechfoolapp.ui.composables.Title
import com.example.czechfoolapp.ui.gameshistoryroute.composables.Date
import com.example.czechfoolapp.ui.gameshistoryroute.composables.LosingScore
import com.example.czechfoolapp.ui.gameshistoryroute.composables.PlayerAndScore
import java.time.LocalDateTime
import java.time.Month

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    game: Game?,
    onEvent: (event: GamesHistoryEvent) -> Unit,
    onNavigateUp: () -> Unit,
    onContinueGameNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onNavigateUp()
    }
    if (game == null) {
        return
    }
    Scaffold(
        topBar = {
            CzechFoolSmallTopAppBar(
                onNavigateUp = onNavigateUp,
                title = { Title(text = stringResource(R.string.game_title, game.id)) }
            )
        },
        floatingActionButton = {
            ContinueButton(
                onClick = {
                    onEvent(GamesHistoryEvent.ContinueGame(
                        gameId = game.id,
                        onContinueGameNavigate = onContinueGameNavigate
                    ))
                          },
                enabled = game.isFinished.not()
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier
    ) {
        GameDetailContent(
            game = game,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(dimensionResource(R.dimen.padding_medium))

        )
    }
}

@Composable
private fun ContinueButton(
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .height(dimensionResource(R.dimen.button_height)),
        enabled = enabled
    ) {
        Text(
            text = stringResource(R.string.continue_game),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}


@Composable
fun GameDetailContent(
    game: Game,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Date(
            dateTime = game.date,
            formatPattern = "EEEE, MMMM d, yyyy, HH:mm",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .alpha(0.7f)
        )
        LosingScore(
            score = game.losingScore,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .alpha(0.7f)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_large)))
        PlayerNamesAndScores(
            players = game.players,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.85f)
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
        players.forEachIndexed { index: Int, player: Player ->
            if (index != 0) {
                Divider(
                    thickness = dimensionResource(R.dimen.divider_small),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.padding_large))
                )
            }
            PlayerAndScore(
                player = player.name,
                score = player.score.toString(),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight(690),
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
    }
}



@Preview
@Composable
fun GameDetailScreenPreview() {
    val gameUiModel = Game(
        id = 154,
        losingScore = 200,
        date = LocalDateTime.of(2023, Month.NOVEMBER, 9, 8, 20),
        players = listOf(
            Player(0,"Niku", 115),
            Player(0,"Anastasia", 9),
            Player(0,"Neka", 198),
            Player(0,"Wiko", 86)
        )
    )
    GameDetailScreen(
        game = gameUiModel,
        onEvent = {},
        onNavigateUp = {},
        onContinueGameNavigate = {}
    )
}















