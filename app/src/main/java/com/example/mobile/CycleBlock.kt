package com.example.mobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.io.Serializable


class CycleBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    var textList: MutableState<List<String>>,
    private var LocalBlockList: MutableState<List<CodeBlock>> = mutableStateOf(emptyList()),
    var conditionBlock: CodeBlock = ArithmeticBlock()
): CodeBlock(variables), Serializable {
    override fun executeBlock(): String {
        var a = 0
        while (a<11) {
            interpretProgram(
                blockList = LocalBlockList,
                textList = textList,
                variables = variables,
                isInner = true
            ) {}
            a+=1
            Thread.sleep(100)
        }
        return ""
    }

    @Composable
    override fun Display() {

    }

    fun addNewBlockToBlockList(valueCodeBlock: CodeBlock){
        LocalBlockList.value = LocalBlockList.value + valueCodeBlock
    }
    companion object {
        private const val serialVersionUID = 172256L
    }
}