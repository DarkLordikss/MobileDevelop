package com.example.mobile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.io.Serializable


class CycleBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    var textList: MutableState<List<String>>,
    var LocalBlockList: MutableState<List<CodeBlock>> = mutableStateOf(emptyList<CodeBlock>()),
    var conditionBlock: CodeBlock = ArithmeticBlock()
): CodeBlock(variables), Serializable {
    override fun executeBlock(): String {
        var a = 0
        addTextToList(textList = textList, "Stroka dlya testa")
        while (a<11) {
            interpretProgram(blockList = LocalBlockList, textList = textList, variables = variables) {}
            a+=1;
        }
        return ""
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display() {

    }

    fun addNewBlockToBlockList(valueCodeBlock: CodeBlock){
        LocalBlockList.value = LocalBlockList.value + valueCodeBlock
    }
    companion object {
        private const val serialVersionUID = 123444L
    }
}