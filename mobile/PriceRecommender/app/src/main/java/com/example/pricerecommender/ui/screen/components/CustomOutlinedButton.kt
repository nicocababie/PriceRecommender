package com.example.pricerecommender.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .fillMaxWidth(0.7f)
            .height(56.dp)
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun PurchaseButtonPreview() {
    PriceRecommenderTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomOutlinedButton(text = "Select products", {})
        }
    }
}