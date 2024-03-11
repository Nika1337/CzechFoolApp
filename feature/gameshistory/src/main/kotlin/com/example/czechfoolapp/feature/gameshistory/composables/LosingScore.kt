package com.example.czechfoolapp.feature.gameshistory.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun LosingScore(
    score: Int,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Losing Score: $score",
        style = style,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}