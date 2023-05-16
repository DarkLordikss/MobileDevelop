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

class InitArrayBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableName: String = "",
    private var variableLength: String = "",
    private var blockLength: CodeBlock? = null,
) : CodeBlock(variables), Serializable {
    private var _nameState = mutableStateOf(variableName)
    private var _valueState = mutableStateOf(variableLength)
    private var _valueBlockState = mutableStateOf(blockLength)

    private val nameState: State<String> = _nameState
    private val valueState: State<String> = _valueState
    private val valueBlockState: State<CodeBlock?> = _valueBlockState

    override fun executeBlock(): String {
        var arrayLength = 0
        if (blockLength != null) {
            blockLength!!.variables = variables
            arrayLength = blockLength!!.executeBlock().toInt()
        }

        else if (variables[variableLength] == null) {
            try {
                arrayLength = variableLength.toInt()
            }
            catch (e: Exception) {
                throw Exception("ERROR: Variable $variableLength is not exist!")
            }
        }

        if (arrayLength < 0){
            throw Exception("ERROR: array length cn npt be less than 0!")
        }

        var i = 0
        while (i < arrayLength){
            variables[variableName+"["+i+"]"] = 0.0
            i ++
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
                        setVariableLength(newValue)
                    },
                    label = { Text("Length", color = White00) }
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

    private fun setVariableLength(value: String) {
        _valueState.value = value
        variableLength = value
    }

    fun setBlockLength(value: CodeBlock) {
        _valueBlockState.value = value
        blockLength = value
    }

    companion object {
        private const val serialVersionUID = 123499L
    }
}