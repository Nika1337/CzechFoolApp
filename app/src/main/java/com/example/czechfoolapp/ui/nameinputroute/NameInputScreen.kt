package com.example.czechfoolapp.ui.nameinputroute

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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
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
import androidx.compose.ui.unit.dp
import com.example.czechfoolapp.R
import com.example.czechfoolapp.ui.CzechFoolTopAppBar

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
            CzechFoolTopAppBar(
                canNavigateBack = true,
                onNavigateUp = onNavigateUp,
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            Button(
                onClick = { onEvent(NameInputEvent.Next(onNavigateToNext)) },
                modifier = Modifier.width(96.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.start_button)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
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
        Spacer(modifier = Modifier.height(12.dp))
        
        nameInputState.forEach { entry ->
            NameTextField(
                entry = entry,
                onValueChange = { value ->
                    onValueChange(entry.key, value)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = if (entry.key <= nameInputState.size) ImeAction.Next else ImeAction.Done
                ),
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.textField_horizontal),
                        vertical = dimensionResource(R.dimen.textField_vertical_small)
                    )
            )
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(
    onValueChange: (String) -> Unit,
    entry: Map.Entry<Int, PlayerNameState>,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions
) {
    Column(
        modifier = modifier
    ){
        OutlinedTextField(
            value = entry.value.name,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Player ${entry.key}")
            },
            keyboardOptions = keyboardOptions,
            singleLine = true,
            isError = entry.value.nameError != null
        )
        entry.value.nameError?.let { nameError -> TextFieldError(textError = nameError) }
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
fun NameInputScreenPreview() {
    NameInputScreen(
        onNavigateToNext = { /*TODO*/ },
        onNavigateUp = { /*TODO*/ },
        onEvent = {},
        nameInputState = mapOf(1 to PlayerNameState("nika"), 2 to PlayerNameState("neka"), 3 to PlayerNameState("taso"))
    )
}










