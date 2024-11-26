package com.example.pricerecommender.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NumberInput(
    title: String,
    inputState: String = "",
    saveInput: (String) -> Unit = {}
) {
    var input by remember { mutableStateOf(inputState) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(inputState) {
        input = inputState
    }

    OutlinedTextField(
        label = { Text(text = title) },
        value = input,
        onValueChange = { newValue ->
            if (newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                input = newValue
                saveInput(input)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                saveInput(input)
                keyboardController?.hide()
            }
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.7f)
    )
}

@Preview(showBackground = true)
@Composable
fun NumberInputPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        NumberInput("Amount", "", {})
    }
}
