package com.example.pricerecommender.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun TextInput(
    title: String,
    inputState: String = "",
    saveInput: (String) -> Unit = {}
) {
    var input by remember { mutableStateOf(inputState) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Text(
            text = title
        )
        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                saveInput(input)
                            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    saveInput(input)
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PurchaseInputPreview() {
    PriceRecommenderTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            TextInput(title = "Store name", "", {})
        }
    }
}