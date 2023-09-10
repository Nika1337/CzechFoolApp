package com.example.czechfoolapp.ui.gameoptions

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.czechfoolapp.R
import com.example.czechfoolapp.data.DefaultValuesSource
import com.example.czechfoolapp.ui.gameoptions.states.ExposedDropDownMenuState
import com.example.czechfoolapp.ui.gameoptions.states.PlayerNumberState
import com.example.czechfoolapp.ui.gameoptions.states.PlayerNumberStateSaver
import com.example.czechfoolapp.ui.gameoptions.states.ScoreState
import com.example.czechfoolapp.ui.gameoptions.states.ScoreStateSaver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameOptionsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {

    Scaffold(
        topBar = {
            GameOptionsScreenAppBar(
                canNavigateBack = false,
                onNavigateUp = onNavigateUp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { innerPadding ->
        MenusAndNextColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            onNavigateToNext = onNavigateToNext
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameOptionsScreenAppBar(
    canNavigateBack: Boolean,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    branding: @Composable () -> Unit = { Branding() }
) {
    CenterAlignedTopAppBar(
        title = branding,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
//            Not Yet Implemented
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    imageVector = Icons.Default.MoreVert,
//                    contentDescription = stringResource(R.string.more_options)
//                )
//            }
        }
    )
}

@Composable
private fun Branding(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ){
        Text(
            text = "Czech",
            fontSize = 32.sp
        )
        Text(
            text = "Fool",
            fontSize = 16.sp
        )
    }
}

@Composable
private fun MenusAndNextColumn(
    onNavigateToNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val playerNumberState by rememberSaveable(stateSaver = PlayerNumberStateSaver) {
        mutableStateOf(PlayerNumberState())
    }
    val scoreState by rememberSaveable(stateSaver = ScoreStateSaver) {
        mutableStateOf(ScoreState())
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenusColumn(
            modifier = Modifier.weight(1f),
            playerNumberState = playerNumberState,
            scoreState = scoreState
        )
        Button(
            onClick = { onNavigateToNext() },
            modifier = Modifier
                .align(alignment = Alignment.End)
                .width(96.dp),
            enabled = playerNumberState.isValid && scoreState.isValid
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
    playerNumberState: ExposedDropDownMenuState,
    scoreState: ExposedDropDownMenuState
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextFieldMenu(
            state = playerNumberState,
            items = DefaultValuesSource.numbersOfPlayers,
            label = R.string.number_of_players
        )
        Spacer(modifier = Modifier.height(48.dp))
        TextFieldMenu(
            state = scoreState,
            items = DefaultValuesSource.scores,
            label = R.string.losing_score,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextFieldMenu(
    state: ExposedDropDownMenuState,
    items: List<String>,
    @StringRes label: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = state.expanded,
            onExpandedChange = {
                state.expanded = !state.expanded
            }
        ) {
            OutlinedTextField(
                value = state.text,
                onValueChange = {
                    state.text = it
                    state.enableShowErrors()
                },
                readOnly = false,
                label = { Text(stringResource(label)) } ,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.expanded) },
                isError = state.showErrors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = state.expanded,
                onDismissRequest = { state.expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            state.text = item
                            state.expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
    state.getError()?.let { error -> TextFieldError(textError = error) }
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
    GameOptionsScreen(onNavigateUp = { /*TODO*/ }, onNavigateToNext = { /*TODO*/ })
}