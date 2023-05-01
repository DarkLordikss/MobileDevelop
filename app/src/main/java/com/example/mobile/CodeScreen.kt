package com.example.mobile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun CodeScreen(blockList: MutableState<List<CodeBlock>>) {
    VerticalReorderList(blockList = blockList)
    BlockMenu(blockList)
}

@Composable
fun DynamicBlockDisplay(blockList: MutableState<List<CodeBlock>>) {
    Column {
        blockList.value.forEach { block ->
            block.Display()
        }
    }
}

@Composable
fun VerticalReorderList(blockList: MutableState<List<CodeBlock>>) {
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        blockList.value = blockList.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
            .padding(top = 50.dp)
    ) {
        items(blockList.value, { it }) { block ->
            ReorderableItem(state, key = block) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                ) {
                    block.Display()
                }
            }
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
                    .clickable(onClick = {
                        addBlockToList(
                            blockList = blockList,
                            newBlock = VariableBlock(
                                variableName = "x",
                                variableValue = "0"
                            )
                        )
                    })
            )
            Text(
                "Arithmetic",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(
                        onClick = {
                            addBlockToList(
                                blockList = blockList,
                                newBlock = ArithmeticBlock(
                                    variableFirst = "0",
                                    variableSecond = "0",
                                    myOperator = "+"
                                )
                            )
                        }
                    )
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
//fun testVars(blockList: MutableState<List<CodeBlock>>) {
//    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "x", variableValue = "-999"))
//    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "y", variableValue = "-777"))
//    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "z", variableValue = "1111"))
//
//    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "x", variableValue = "3.5"))
//    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "y", variableValue = "1"))
//    addBlockToList(blockList = blockList, newBlock = VariableBlock(variableName = "z", variableValue = "x"))
//
//    val testBlock = VariableBlock()
//    testBlock.setVariableName("TEST")
//    testBlock.setBlockValue(
//        ArithmeticBlock(
//            variableFirst = "4",
//            blockSecond = ArithmeticBlock(variableFirst = "x", variableSecond = "y", myOperator = "-"),
//            myOperator = "*"),
//    )
//
//    addBlockToList(blockList = blockList, newBlock = testBlock)
//}