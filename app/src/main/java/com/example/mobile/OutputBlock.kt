package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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

class OutputBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var textList: MutableState<List<String>>,
    private var valueToPrint: String = "",
    private var blockValue: CodeBlock? = null,
): CodeBlock(variables), Serializable {
    private var _valueToPrintState = mutableStateOf(valueToPrint)
    private var _valueBlockState = mutableStateOf(blockValue)

    private val valueToPrintState: State<String> = _valueToPrintState
    private val valueBlockState: State<CodeBlock?> = _valueBlockState

    override fun executeBlock(): String {
        var normalValueToPrint = getNormalizedName(valueToPrint, variables)
        val value = variables[normalValueToPrint]

        if (blockValue != null) {
            blockValue!!.variables = variables
            addTextToList(textList = textList, newText = blockValue!!.executeBlock())
        }
        else if (value != null) {
            addTextToList(textList = textList, newText = "VARIABLE $valueToPrint is $value")
        }
        else {
            addTextToList(textList = textList, newText = valueToPrint)
        }
        return ""
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
            Text("Print ")
            if (valueBlockState.value == null) {
                OutlinedTextField(
                    modifier = Modifier.width(100.dp),
                    value = valueToPrintState.value,
                    onValueChange = { newValue ->
                        setValueToPrint(newValue)
                    },
                    label = { Text("Value", color = White00) }
                )
            }
            else {
                valueBlockState.value!!.Display()
            }
        }
    }

    private fun setValueToPrint(value: String) {
        _valueToPrintState.value = value
        valueToPrint = value
    }

    fun setBlockValue(value: CodeBlock) {
        _valueBlockState.value = value
        blockValue = value
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
        private const val serialVersionUID = 123455L
    }
}