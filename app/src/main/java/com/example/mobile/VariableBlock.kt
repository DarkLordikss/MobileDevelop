package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.White00
import java.io.Serializable

class VariableBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableName: String = "",
    private var variableValue: String = "",
    private var blockValue: CodeBlock? = null,
) : CodeBlock(variables), Serializable {
    private var _nameState = mutableStateOf(variableName)
    private var _valueState = mutableStateOf(variableValue)
    private var _valueBlockState = mutableStateOf(blockValue)

    private val nameState: State<String> = _nameState
    private val valueState: State<String> = _valueState
    private val valueBlockState: State<CodeBlock?> = _valueBlockState

    override fun executeBlock(): String {
        val _variableName = getNormalizedName(variableName, variables)
        println("norm name in variableBlock: ${_variableName}")

        if (blockValue != null) {
            blockValue!!.variables = variables
            val newValue = blockValue!!.executeBlock().toDouble()
            try {
                variables[_variableName] = newValue
            } catch (e: Exception) {
                throw Exception("ERROR: Variable ${_variableName} is not exist!")
            }
        } else {
            val _variableValue = getNormalizedName(variableValue, variables)
            if (variables[_variableValue] == null) {
                try {
                    variables[_variableName] = _variableValue.toDouble()
                } catch (e: Exception) {
                    throw Exception("ERROR: Variable $_variableValue is not exist!")
                }
            } else {
                variables[_variableName] = variables[_variableValue]!!
            }
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
                .border(2.dp, MaterialTheme.colorScheme.secondary, shape)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(10.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = nameState.value,
                onValueChange = { newValue ->
                    setVariableName(newValue)
                },
                label = { Text("Name", color = White00) }
            )
            Text(" = ")
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

    private fun setVariableName(name: String) {
        _nameState.value = name
        variableName = name
    }

    private fun setVariableValue(value: String) {
        _valueState.value = value
        variableValue = value
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
        private const val serialVersionUID = 123456L
    }
}