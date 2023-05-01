package com.example.mobile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class VariableBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableName: String = "",
    private var variableValue: String = "",
    private var blockValue: CodeBlock? = null,
) : CodeBlock(variables) {
    private var _nameState = mutableStateOf(variableName)
    private var _valueState = mutableStateOf(variableValue)

    private val nameState: State<String> = _nameState
    private val valueState: State<String> = _valueState

    override fun executeBlock(): String {
        if (blockValue != null) {
            blockValue!!.variables = variables
            variables[variableName] = blockValue!!.executeBlock().toDouble()
        }

        else if (variables[variableValue] == null) {
            try {
                variables[variableName] = variableValue.toDouble()
            }
            catch (e: Exception) {
                throw Exception("ERROR: Variable $variableValue is not exist!")
            }
        }
        else {
            variables[variableName] = variables[variableValue]!!
        }

        return ""
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = nameState.value,
                onValueChange = { newValue ->
                    setVariableName(newValue)
                },
                label = { Text("Variable Name") }
            )
            Text(" = ")
            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = valueState.value,
                onValueChange = { newValue ->
                    setVariableValue(newValue)
                },
                label = { Text("Variable Value") }
            )
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
}