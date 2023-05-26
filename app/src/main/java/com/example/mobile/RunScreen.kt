package com.example.mobile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.Green00
import com.example.mobile.ui.theme.Red00

@Composable
fun RunScreen(textList: MutableState<List<String>>, blockList: MutableState<List<CodeBlock>>) {
    val isButtonEnabled = remember { mutableStateOf(true) }

    if (blockList.value.isEmpty()) {
        isButtonEnabled.value = false
    }
    Box (modifier = Modifier.fillMaxSize()) {
        Column {
            DynamicTextDisplay(textList.value.toMutableList())
        }

        Button(
            onClick = {
                if (blockList.value.isNotEmpty() && isButtonEnabled.value) {
                    isButtonEnabled.value = false
                    interpretProgram(blockList = blockList, textList = textList) {
                        isButtonEnabled.value = true
                    }
                }
            },
            enabled = isButtonEnabled.value,
            modifier = Modifier
                .padding(
                    bottom = 120.dp,
                    end = 40.dp
                )
                .size(64.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
        ) {
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = "Start code"
            )
        }
    }
}

@Composable
fun DynamicTextDisplay(textList: MutableList<String>) {
    val scrollState = rememberScrollState()

    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
        .padding(
            bottom = 90.dp,
            top = 16.dp,
            start = 16.dp,
            end = 16.dp
        )) {
        textList.forEach { text ->
            if (text.length >= 5) {
                if (text.slice(0..4) == "ERROR") {
                    Text(
                        text = text,
                        color = Red00
                    )
                }
            }
            if (text.length >= 8) {
                if (text.slice(0..7) == "VARIABLE") {
                    Text(
                        text = text,
                        color = Green00
                    )
                }
            }
            else {
                Text(text = text)
            }
        }
    }

    LaunchedEffect(textList) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
}

fun addTextToList(textList: MutableState<List<String>>, newText: String) {
    textList.value = textList.value + newText
}
