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

class ConditionalBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var booleanBlock: CodeBlock,
    private val trueNode: MutableState<List<CodeBlock>> = mutableStateOf(emptyList()),
    private val falseNode: MutableState<List<CodeBlock>> = mutableStateOf(emptyList()),
    private var textList: MutableState<List<String>>,
): CodeBlock(variables), Serializable {
    private var _booleanBlockState = mutableStateOf(booleanBlock)

    private val booleanBlockState: State<CodeBlock> = _booleanBlockState
    override fun executeBlock(): String {
        booleanBlock.variables = variables
        val condition = booleanBlock.executeBlock()

        if (trueNode.value.isEmpty()) {
            throw Exception("ERROR: empty Conditional Block body")
        }

        if (condition.toDouble() > 0){
            interpretProgram(blockList = trueNode, textList = textList, isInner = true, variables = variables){}
        } else {
            if (falseNode.value.isNotEmpty()){
                interpretProgram(blockList = falseNode, textList = textList, isInner = true, variables = variables){}
            }
        }

        return "0"
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
                    Text(text = "if: ")
                    booleanBlockState.value.Display()
                }
                trueNode.value.forEach { block ->
                    block.Display()
                }
                if (falseNode.value.isNotEmpty()){
                    Row {
                        Text(text = "else: ")
                    }
                    falseNode.value.forEach { block ->
                        block.Display()
                    }
                }
            }
        }
    }

    fun addCondition(block:CodeBlock) {
        _booleanBlockState.value = block
        booleanBlock = block
    }
    fun addToTrueNode(block:CodeBlock) {
        trueNode.value = trueNode.value + block
    }

    fun addToFalseNode(block:CodeBlock) {
        falseNode.value = falseNode.value + block
    }

    private fun getNormalizedName(name: String, variables: MutableMap<String, Double>): String {
        val regular_name = "(?:[a-zA-Z]+[-_]*)+".toRegex()
        var new_name = name
        println("renaming start: ${new_name}")
        if (checkArrayIndex(name)){
            val matches = regular_name.findAll(name).map{it.value}.toList()
            try {
                new_name = "${matches[0]}[${variables[matches[1]]!!.toInt()}]"
            }
            catch (e: Exception) {
                throw Exception("ERROR: Variable ${matches[1]} is not exist!")
            }
        }
        println("renaming end: ${new_name}")
        return new_name
    }
    private fun checkArrayIndex(value: String): Boolean {
        val regular_array = "(?:[a-zA-Z]+[-_]*)+\\[(?:[a-zA-Z]+[-_]*)+\\]".toRegex()
        val answer = regular_array.matches(value)
        println("checking: ${answer}")
        return answer
    }

    companion object {
        private const val serialVersionUID = 122256L
    }
}