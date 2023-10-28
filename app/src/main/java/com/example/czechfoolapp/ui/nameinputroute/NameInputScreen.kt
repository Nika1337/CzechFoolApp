package com.example.czechfoolapp.ui.nameinputroute

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
    nameInputState: Map<Int, String>,
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldsColumn(
    onValueChange: (id: Int, value: String) -> Unit,
    nameInputState: Map<Int, String>,
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
            OutlinedTextField(
                value = entry.value,
                onValueChange = { value ->
                    onValueChange(entry.key, value)
                },
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.textField_horizontal),
                        vertical = dimensionResource(R.dimen.textField_vertical_small)
                    )
                    .fillMaxWidth(),
                label = {
                    Text(text = "Player ${entry.key}")
                }
            )
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Preview
@Composable
fun NameInputScreenPreview() {
    NameInputScreen(
        onNavigateToNext = { /*TODO*/ },
        onNavigateUp = { /*TODO*/ },
        onEvent = {},
        nameInputState = mapOf(1 to "Nika", 2 to "Taso", 3 to "Neka", 4 to "")
    )
}










