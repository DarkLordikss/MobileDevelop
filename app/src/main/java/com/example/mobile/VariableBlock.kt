package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.secondary, shape)
                .fillMaxWidth()
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
            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = valueState.value,
                onValueChange = { newValue ->
                    setVariableValue(newValue)
                },
                label = { Text("Value", color = White00) }
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

    fun setBlockValue(value: CodeBlock) {
        blockValue = value
    }

    companion object {
        private const val serialVersionUID = 123456L
    }
}