package com.example.czechfoolapp.feature.gameshistory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.core.designsystem.component.CzechFoolTopAppBar
import com.example.czechfoolapp.core.designsystem.component.Title
import com.example.czechfoolapp.core.model.Game
import com.example.czechfoolapp.core.model.Player
import com.example.czechfoolapp.feature.gameshistory.composables.Date
import com.example.czechfoolapp.feature.gameshistory.composables.LosingScore
import com.example.czechfoolapp.feature.gameshistory.composables.PlayerAndScore
import java.time.LocalDateTime
import java.time.Month

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    game: Game?,
    onEvent: (event: GamesHistoryEvent) -> Unit,
    onNavigateContinueGame: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null
) {
    if (onNavigateUp != null) {
        BackHandler {
            onNavigateUp()
        }
    }
    if (game == null) {
        NoGameSelected(
            modifier = modifier
        )
        return
    }
    Scaffold(
        topBar = {
            CzechFoolTopAppBar(
                onNavigateUp = onNavigateUp,
                title = { Title(text = stringResource(R.string.game_title, game.id)) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_up)
                    )
                }
            )
        },
        floatingActionButton = {
            ContinueButton(
                onClick = {
                    onEvent(
                        GamesHistoryEvent.ContinueGame(
                        gameId = game.id,
                        onContinueGameNavigate = onNavigateContinueGame
                        )
                    )
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
fun NoGameSelected(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = stringResource(R.string.no_game_selected),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onBackground
                .copy(alpha = 0.5f)
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
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_large)))
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
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.padding_large)),
                    thickness = dimensionResource(R.dimen.divider_small),
                    color = MaterialTheme.colorScheme.onBackground
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
        onNavigateContinueGame = {},
    )
}















