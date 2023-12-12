package com.example.czechfoolapp.ui.nameinputroute

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R
import com.example.czechfoolapp.ui.composables.CzechFoolSmallTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameInputScreen(
    onNavigateToNext: () -> Unit,
    onNavigateUp: () -> Unit,
    onEvent: (event: NameInputEvent) -> Unit,
    nameInputState: Map<Int, PlayerNameState>,
) {
    Scaffold(
        topBar = {
            CzechFoolSmallTopAppBar(
                onNavigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(NameInputEvent.Next(onNavigateToNext)) },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.start_new_game)
                )
            }
        }
    ) {
        TextFieldsColumn(
            onValueChange = { id: Int, value: String ->
                onEvent(NameInputEvent.PlayerNameChanged(id = id, value = value))
            },
            nameInputState = nameInputState,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }
}



@Composable
fun TextFieldsColumn(
    onValueChange: (id: Int, value: String) -> Unit,
    nameInputState: Map<Int, PlayerNameState>,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        nameInputState.forEach { (id: Int, playerNameState: PlayerNameState) ->
            NameTextField(
                id = id,
                playerNameState = playerNameState,
                onValueChange = { value ->
                    onValueChange(id, value)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = if (id <= nameInputState.size) ImeAction.Next else ImeAction.Done
                ),
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.textField_horizontal),
                        vertical = dimensionResource(R.dimen.textField_vertical_small)
                    )
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.bottom_space)))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(
    id: Int,
    playerNameState: PlayerNameState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
) {
    Log.d("recomposedNameTextField", playerNameState.name)
    Column(
        modifier = modifier
    ){
        OutlinedTextField(
            value = playerNameState.name,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Player $id")
            },
            keyboardOptions = keyboardOptions,
            singleLine = true,
            isError = playerNameState.nameError != null
        )
        playerNameState.nameError?.let { nameError -> TextFieldError(textError = nameError) }
    }
}

@Composable
private fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.textFieldError)))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
fun NameInputScreenPreview() {
    NameInputScreen(
        onNavigateToNext = { /*TODO*/ },
        onNavigateUp = { /*TODO*/ },
        onEvent = {},
        nameInputState = mapOf(1 to PlayerNameState("nika"), 2 to PlayerNameState("neka"), 3 to PlayerNameState("taso"))
    )
}










