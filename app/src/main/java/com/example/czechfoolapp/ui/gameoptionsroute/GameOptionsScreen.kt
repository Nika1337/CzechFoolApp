package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.czechfoolapp.R
import com.example.czechfoolapp.data.DefaultValuesSource
import com.example.czechfoolapp.ui.CzechFoolTopAppBar
import com.example.czechfoolapp.ui.gameoptionsroute.states.GameOptionState
import com.example.czechfoolapp.ui.gameoptionsroute.states.GameOptionsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameOptionsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit,
    gameOptionState: GameOptionsState,
    onEvent: (event: GameOptionEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CzechFoolTopAppBar(
                canNavigateBack = false,
                onNavigateUp = onNavigateUp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { innerPadding ->
        MenusAndNextColumn(
            onNavigateToNext = onNavigateToNext,
            gameOptionsState = gameOptionState,
            onEvent = onEvent,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}


@Composable
private fun MenusAndNextColumn(
    onNavigateToNext: () -> Unit,
    modifier: Modifier = Modifier,
    onEvent: (event: GameOptionEvent) -> Unit,
    gameOptionsState: GameOptionsState
) {
    Box(
        modifier = modifier
    ) {
        MenusColumn(
            modifier = Modifier
                .fillMaxSize(),
            gameOptionsState = gameOptionsState,
            onEvent = onEvent
        )
        Button(
            onClick = {
                      onEvent(GameOptionEvent.Next(onNavigateToNext))
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .width(96.dp),
        ) {
            Text(
               text = stringResource(id = R.string.next_button)
            )
        }
    }
}

@Composable
private fun MenusColumn(
    modifier: Modifier = Modifier,
    gameOptionsState: GameOptionsState,
    onEvent: (event: GameOptionEvent) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextFieldMenu(
            onEvent = { value: TextFieldValue -> onEvent(GameOptionEvent.NumberOfPlayersChanged(value)) },
            state = gameOptionsState.numberOfPlayersState,
            items = DefaultValuesSource.numbersOfPlayers,
            label = R.string.number_of_players,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(24.dp)
        )
        TextFieldMenu(
            onEvent = { value: TextFieldValue -> onEvent(GameOptionEvent.LosingScoreChanged(value)) },
            state = gameOptionsState.losingScoreState,
            items = DefaultValuesSource.scores,
            label = R.string.losing_score,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.padding(24.dp)
        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextFieldMenu(
    onEvent: (TextFieldValue) -> Unit,
    state: GameOptionState,
    items: List<TextFieldValue>,
    @StringRes label: Int,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.value,
                    onValueChange = {
                        onEvent(it)
                    },
                    singleLine = true,
                    readOnly = true,
                    label = { Text(stringResource(label)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    isError = state.errorMessage != null,
                    keyboardOptions = keyboardOptions,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item.text,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                   },
                            onClick = {
                                onEvent(item)
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
        state.errorMessage?.let { error -> TextFieldError(textError = error) }
    }
}

@Composable
private fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        )
    }
}


@Preview
@Composable
fun GameOptionsScreenPreview() {
    GameOptionsScreen(
        onNavigateUp = { /*TODO*/ },
        onNavigateToNext = { /*TODO*/ },
        gameOptionState = GameOptionsState(),
        onEvent = {}
    )
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun GameOptionsScreenPreviewLandscape() {
    GameOptionsScreen(
        onNavigateUp = { /*TODO*/ },
        onNavigateToNext = { /*TODO*/ },
        gameOptionState = GameOptionsState(),
        onEvent = {}
    )
}