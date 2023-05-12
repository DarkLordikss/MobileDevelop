package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.White00
import java.io.Serializable

class ConditionalBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var booleanBlock: BooleanBlock,
    private val trueNode: MutableState<List<CodeBlock>> = mutableStateOf(emptyList()),
    private val falseNode: MutableState<List<CodeBlock>> = mutableStateOf(emptyList()),
    private var textList: MutableState<List<String>>,
): CodeBlock(variables), Serializable {
    override fun executeBlock(): String {
        var condition = booleanBlock.executeBlock()
        if (condition.toDouble() > 0){
            if (trueNode.value.isNotEmpty()){
                interpretProgram(blockList = trueNode, textList = textList, isInner = true){}
            }else{
                throw Exception("ERROR: empty Conditional Block body")
            }
            if (falseNode.value.isNotEmpty()){
                interpretProgram(blockList = falseNode, textList = textList, isInner = true){}
            }
        }

        return "0"
    }

    @OptIn(ExperimentalMaterial3Api::class)
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
            Column(

            ) {
                Row() {
                    Text(text = "if: ")
                    booleanBlock.Display()
                }
                trueNode.value.forEach { block ->
                    block.Display()
                }
                if (falseNode.value.isNotEmpty()){
                    Row() {
                        Text(text = "else: ")
                    }
                    falseNode.value.forEach { block ->
                        block.Display()
                    }
                }
            }
        }
    }

    fun addToTrueNode(block:CodeBlock){
        trueNode.value = trueNode.value + block
    }

    fun addToFalseNode(block:CodeBlock){
        falseNode.value = falseNode.value + block
    }

    companion object {
        private const val serialVersionUID = 122256L
    }
}