package com.example.czechfoolapp.ui.composables

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CzechFoolSmallTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null,
    title: @Composable () -> Unit = { Branding(modifier = modifier.height(dimensionResource(R.dimen.logo_height))) },
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: @Composable () -> Unit = { Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = stringResource(R.string.back_button)
    ) }
) {
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            if (onNavigateUp != null) {
                IconButton(onClick = onNavigateUp) {
                    navigationIcon()
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SmallBarPreview() {
    CzechFoolSmallTopAppBar()
}















