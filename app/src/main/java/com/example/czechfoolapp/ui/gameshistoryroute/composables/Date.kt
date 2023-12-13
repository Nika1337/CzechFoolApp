package com.example.czechfoolapp.ui.gameshistoryroute.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun Date(
    dateTime: LocalDateTime,
    formatPattern: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    Text(
        text = dateTime.format(DateTimeFormatter.ofPattern(formatPattern, Locale.ENGLISH)),
        style = style,
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}