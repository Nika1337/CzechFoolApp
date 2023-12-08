package com.example.czechfoolapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.czechfoolapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CzechFoolTopAppBar(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = false,
    onNavigateUp: () -> Unit = {},
    branding: @Composable () -> Unit = { Branding(modifier = modifier.height(dimensionResource(R.dimen.logo_height))) }
) {
    CenterAlignedTopAppBar(
        title = branding,
        modifier = modifier.fillMaxWidth(),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}


@Composable
private fun Branding(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.czech_fool_logo),
        contentDescription = stringResource(R.string.czech_fool),
        modifier = modifier
    )
}