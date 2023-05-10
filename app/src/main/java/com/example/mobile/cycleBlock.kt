package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.White00
import java.io.Serializable

class cycleBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableFirst: String = "",
    private var variableSecond: String = "",
    private var myOperator: String = "",
    private var blockFirst: CodeBlock? = null,
    private var blockSecond: CodeBlock? = null
): CodeBlock(variables), Serializable {
    private val operatorsArray = mapOf(
        "<" to 0,
        ">" to 1,
        "<=" to 2,
        ">=" to 3,
        "=" to 4
    )

    private var _firstValueState = mutableStateOf(variableFirst)
    private var _secondValueState = mutableStateOf(variableSecond)
    private var _firstBlockState = mutableStateOf(blockFirst)
    private var _secondBlockState = mutableStateOf(blockSecond)

    private val firstValueState: State<String> = _firstValueState
    private val secondValueState: State<String> = _secondValueState
    private val firstBlockState: State<CodeBlock?> = _firstBlockState
    private val secondBlockState: State<CodeBlock?> = _secondBlockState

    override fun executeBlock(): String {
        var firstOperand: Double?
        var secondOperand: Double?

        val operId = operatorsArray[myOperator]

        if (blockFirst == null) {
            firstOperand = variables[variableFirst]
            if (firstOperand == null) {
                try {
                    firstOperand = variableFirst.toDouble()
                }
                catch (e: Exception){
                    throw Exception("ERROR: Variable $variableFirst is not exist!")
                }
            }
        }
        else {
            blockFirst!!.variables = variables
            firstOperand = blockFirst!!.executeBlock().toDouble()
        }

        if (blockSecond == null) {
            secondOperand = variables[variableSecond]
            if (secondOperand == null) {
                try {
                    secondOperand = variableSecond.toDouble()
                }
                catch (e: Exception){
                    throw Exception("ERROR: Variable $variableSecond is not exist!")
                }
            }
        }
        else {
            blockSecond!!.variables = variables
            secondOperand = blockSecond!!.executeBlock().toDouble()
        }

        var myAnswer = ""

        when (operId) {
            0 -> {
                while (firstOperand < secondOperand) {
                    do условие
                }
            }
            1 -> {
                while (firstOperand > secondOperand) {
                    do условие
                }
            }
            2 -> {
                while (firstOperand <= secondOperand) {
                    do условие
                }
            }
            3 -> {
                while (firstOperand >= secondOperand) {
                    do условие
                }
            }
            4 -> {
                while (firstOperand == secondOperand) {
                    do условие
                }
            }
        }

        return myAnswer
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display() {
        val operators = listOf("<", ">", "<=", ">=", "=")
        val selectedOperator = remember { mutableStateOf(myOperator) }
        var expanded by remember { mutableStateOf(false) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.secondary, AlertDialogDefaults.shape)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(10.dp)
        ) {
            if (firstBlockState.value == null) {
                OutlinedTextField(
                    modifier = Modifier.width(100.dp),
                    value = firstValueState.value,
                    onValueChange = { newValue ->
                        setVariableFirst(newValue)
                    },
                    label = { Text("While", color = White00) },
                )
            }
            else {
                firstBlockState.value!!.Display()
            }
            IconButton(onClick = { expanded = true }) {
                Text(text = selectedOperator.value)
            }
            DropdownMenu(
                modifier = Modifier.width(50.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                operators.forEach { operator ->
                    Text(
                        text = operator,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                onClick = {
                                    selectedOperator.value = operator
                                    myOperator = operator
                                }
                            )
                    )
                }
            }
            if (secondBlockState.value == null) {
                OutlinedTextField(
                    modifier = Modifier.width(100.dp),
                    value = secondValueState.value,
                    onValueChange = { newValue ->
                        setVariableSecond(newValue)
                    },
                    label = { Text("Operand", color = White00) }
                )
            }
            else {
                secondBlockState.value!!.Display()
            }
        }
    }

    private fun setVariableFirst(firstValue: String) {
        _firstValueState.value = firstValue
        variableFirst = firstValue
    }

    private fun setVariableSecond(secondValue: String) {
        _secondValueState.value = secondValue
        variableSecond = secondValue
    }

    fun setFirstBlock(block: CodeBlock) {
        _firstBlockState.value = block
        blockFirst = block
    }

    fun setSecondBlock(block: CodeBlock) {
        _secondBlockState.value = block
        blockSecond = block
    }

    companion object {
        private const val serialVersionUID = 123444L
    }
}