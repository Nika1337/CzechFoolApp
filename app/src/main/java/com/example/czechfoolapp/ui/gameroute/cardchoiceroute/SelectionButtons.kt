package com.example.czechfoolapp.ui.gameroute.cardchoiceroute

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.czechfoolapp.R

interface SelectionButtons {
    
    @Composable
    operator fun invoke(
        count: Int,
        onCountChange: (Int) -> Unit,
        modifier: Modifier
    )
}

class RadioSelectionButton : SelectionButtons {
    @Composable
    override fun invoke(
        count: Int,
        onCountChange: (Int) -> Unit,
        modifier: Modifier
    ) {
        RadioButton(
            selected = count != 0,
            onClick = { onCountChange( if (count == 1) 0 else 1 ) },
            modifier = modifier
        )
    }

}

class PlusMinusSelectionButtons : SelectionButtons {
    @Composable
    override fun invoke(
        count: Int,
        onCountChange: (Int) -> Unit,
        modifier: Modifier
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (count != 0){
                MinusButton(
                    onClick = { onCountChange(count - 1) },
                    modifier = Modifier.size(dimensionResource(R.dimen.count_size))
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_mini)))
                Count(
                    count = count,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.count_size))
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_mini)))
            }
            PlusButton(
                onClick = { onCountChange(count + 1) },
                modifier = Modifier.size(dimensionResource(R.dimen.count_size))
            )
        }
    }

    @Composable
    private fun Count(
        count: Int,
        modifier: Modifier = Modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(MaterialTheme.colorScheme.background, CircleShape)
        ){
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }

    @Composable
    private fun PlusButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
       IconButton(
           onClick = onClick,
           modifier = modifier,
       ) {
           Icon(
               painter = painterResource(R.drawable.plus_icon),
               contentDescription = stringResource(R.string.plus_button),
               modifier = Modifier
           )
       }
    }

    @Composable
    private fun MinusButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Icon(
                painter = painterResource(R.drawable.minus_icon),
                contentDescription = stringResource(R.string.remove_button),
                modifier = Modifier
            )
        }
    }

}

@Preview
@Composable
fun RadioSelectionButtonPreview() {
    val selectionButtons = RadioSelectionButton()

    selectionButtons(
        count = 1,
        onCountChange = {},
        modifier = Modifier
    )

}

@Preview
@Composable
fun PlusMinusSelectionButtonPreview() {
    val selectionButtons = PlusMinusSelectionButtons()

    selectionButtons(
        count = 1,
        onCountChange = {},
        modifier = Modifier
    )
}