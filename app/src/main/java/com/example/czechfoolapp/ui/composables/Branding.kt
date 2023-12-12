package com.example.czechfoolapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.czechfoolapp.R


@Composable
fun Branding(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.czech_fool_logo),
        contentDescription = stringResource(R.string.czech_fool),
        modifier = modifier
    )
}