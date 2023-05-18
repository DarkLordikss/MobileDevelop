package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.White00
import java.io.Serializable


class CycleBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    var textList: MutableState<List<String>>,
    private var LocalBlockList: MutableState<List<CodeBlock>> = mutableStateOf(emptyList()),
    private var variableValue: String = "",
    private var blockValue: CodeBlock? = null,
): CodeBlock(variables), Serializable {
    private var _valueState = mutableStateOf(variableValue)
    private var _valueBlockState = mutableStateOf(blockValue)
    private val valueState: State<String> = _valueState
    private val valueBlockState: State<CodeBlock?> = _valueBlockState

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.secondary, AlertDialogDefaults.shape)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(10.dp)
        ) {
            Text("While ")
            if (valueBlockState.value == null) {
                OutlinedTextField(
                    modifier = Modifier.width(100.dp),
                    value = valueState.value,
                    onValueChange = { newValue ->
                        setVariableValue(newValue)
                    },
                    label = { Text("Value", color = White00) }
                )
            }
            else {
                valueBlockState.value!!.Display()
            }
        }
    }

    fun addNewBlockToBlockList(valueCodeBlock: CodeBlock){
        LocalBlockList.value = LocalBlockList.value + valueCodeBlock
    }
    companion object {
        private const val serialVersionUID = 172256L
    }
}
