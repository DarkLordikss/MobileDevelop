package com.example.mobile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeScreen(blockList: MutableState<List<CodeBlock>>) {
    BlockMenu(blockList)
}

@Composable
fun DynamicBlockDisplay(blockList: MutableList<CodeBlock>) {
    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)) {
        blockList.forEach { block ->
            /* TODO */
        }
    }
}

fun addBlockToList(blockList: MutableState<List<CodeBlock>>, newBlock: CodeBlock) {
    blockList.value = blockList.value + newBlock
}

@Composable
fun BlockMenu(blockList: MutableState<List<CodeBlock>>) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Filled.List, contentDescription = "Show menu")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Text(
                "Variable",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = { testVars(blockList) })
            )
            Text(
                "Placeholder",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {})
            )
            Divider()
            Text(
                "Placeholder",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {})
            )
        }
    }
}

//For TEST ONLY
fun testVars(blockList: MutableState<List<CodeBlock>>) {
    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "x", variableValue = "-999"))
    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "y", variableValue = "-777"))
    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "z", variableValue = "1111"))

    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "x", variableValue = "3"))
    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "y", variableValue = "1"))
    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "z", variableValue = "x"))

    val testBlock = VariableBlock()
    testBlock.setVariableName("TEST")
    testBlock.setVariableValue("z")

    addBlockToList(blockList = blockList, newBlock = testBlock)
}