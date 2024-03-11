package com.example.czechfoolapp.core.designsystem.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CzechFoolTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null,
    title: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: @Composable () -> Unit = {}
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
    CzechFoolTopAppBar()
}















