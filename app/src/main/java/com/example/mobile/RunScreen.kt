package com.example.mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RunScreen(textList: MutableState<List<String>>, blockList: MutableState<List<CodeBlock>>) {
    val isButtonEnabled = remember { mutableStateOf(true) }

    if (blockList.value.isEmpty()) {
        isButtonEnabled.value = false
    }

    Column {
        Button(
            onClick = {
                if (blockList.value.isNotEmpty() && isButtonEnabled.value) {
                    isButtonEnabled.value = false
                    interpretProgram(blockList = blockList, textList = textList) {
                        isButtonEnabled.value = true
                    }
                }
            },
            enabled = isButtonEnabled.value
        ) {
            Text(text = "Start code")
        }
        DynamicTextDisplay(textList.value.toMutableList())
    }
}

@Composable
fun DynamicTextDisplay(textList: MutableList<String>) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)) {
            textList.forEach { text ->
                Text(text = text)
            }
        }
        LaunchedEffect(textList) {}
}

fun addTextToList(textList: MutableState<List<String>>, newText: String) {
    textList.value = textList.value + newText
}
