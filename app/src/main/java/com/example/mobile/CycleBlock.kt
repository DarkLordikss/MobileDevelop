package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import java.io.Serializable


class CycleBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    var textList: MutableState<List<String>>,
    private var LocalBlockList: MutableState<List<CodeBlock>> = mutableStateOf(emptyList()),
    private var booleanBlock: CodeBlock,
): CodeBlock(variables), Serializable {
    private var _booleanBlockState = mutableStateOf(booleanBlock)
    private val booleanBlockState: State<CodeBlock> = _booleanBlockState

    override fun executeBlock(): String {
        booleanBlock.variables = variables
        while (booleanBlock.executeBlock() == "1") {
            interpretProgram(
                blockList = LocalBlockList,
                textList = textList,
                variables = variables,
                isInner = true
            ) {}
            booleanBlock.variables = variables
        }
        return ""
    }

    @Composable
    override fun Display() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.secondary, AlertDialogDefaults.shape)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(10.dp)
        ) {
            Column {
                Row {
                    Text(text = "While: ")
                    booleanBlockState.value.Display()
                }
                LocalBlockList.value.forEach { block ->
                    block.Display()
                }
            }
        }
    }

    fun addToLocalBlockList(block:CodeBlock) {
        LocalBlockList.value = LocalBlockList.value + block
    }

    fun addCondition(block:CodeBlock) {
        _booleanBlockState.value = block
        booleanBlock = block
    }
    companion object {
        private const val serialVersionUID = 972256L
    }
}
