package com.example.pricerecommender.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.R

@Composable
fun Counter(
    currentAmount: Int,
    onDecrementClick: () -> Unit,
    onIncrementClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(70.dp)
    ) {
        Text(
            text = stringResource(R.string.add_product_amount),
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        IconButton(
            onClick = onDecrementClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = stringResource(R.string.decrease_amount)
            )
        }
        Text(
            text = currentAmount.toString(),
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = onIncrementClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = stringResource(R.string.increase_amount)
            )
        }
    }
}