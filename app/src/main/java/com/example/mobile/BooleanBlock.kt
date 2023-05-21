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

class BooleanBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var leftVariable: String = "",
    private var rightVariable: String = "",
    private var condition: String = "",
    private var leftBlock: CodeBlock? = null,
    private var rightBlock: CodeBlock? = null
): CodeBlock(variables), Serializable {
    private var _leftValueState = mutableStateOf(leftVariable)
    private var _rightValueState = mutableStateOf(rightVariable)
    private var _leftBlockState = mutableStateOf(leftBlock)
    private var _rightBlockState = mutableStateOf(rightBlock)

    private val firstValueState: State<String> = _leftValueState
    private val secondValueState: State<String> = _rightValueState
    private val firstBlockState: State<CodeBlock?> = _leftBlockState
    private val secondBlockState: State<CodeBlock?> = _rightBlockState
    override fun executeBlock(): String {
        val conditionsArray = mapOf(
            "==" to 0,
            "!=" to 1,
            ">" to 2,
            "<" to 3,
            ">=" to 4,
            "<=" to 5,
            "and" to 6,
            "or" to 7
        )

        var firstOperand: Double?
        var secondOperand: Double?

        val conditionID = conditionsArray[condition]

        if (leftBlock == null) {
            var _leftVariable = getNormalizedName(leftVariable, variables)
            firstOperand = variables[leftVariable]
            if (firstOperand == null) {
                try {
                    firstOperand = leftVariable.toDouble()
                }
                catch (e: Exception){
                    throw Exception("ERROR: Variable $leftVariable is not exist!")
                }
            }
        }
        else {
            leftBlock!!.variables = variables
            firstOperand = leftBlock!!.executeBlock().toDouble()
        }

        if (rightBlock == null) {
            var _rightVariable = getNormalizedName(rightVariable, variables)
            secondOperand = variables[_rightVariable]
            if (secondOperand == null) {
                try {
                    secondOperand = rightVariable.toDouble()
                }
                catch (e: Exception){
                    throw Exception("ERROR: Variable $rightVariable is not exist!")
                }
            }
        }
        else {
            rightBlock!!.variables = variables
            secondOperand = rightBlock!!.executeBlock().toDouble()
        }

        when (conditionID) {
            0 -> {
                return if (firstOperand == secondOperand) {
                    "1"
                } else {
                    "0"
                }
            }

            1 -> {
                return if (firstOperand != secondOperand) {
                    "1"
                } else {
                    "0"
                }
            }

            2 -> {
                return if (firstOperand > secondOperand) {
                    "1"
                } else {
                    "0"
                }
            }

            3 -> {
                return if (firstOperand < secondOperand) {
                    "1"
                } else {
                    "0"
                }
            }

            4 -> {
                return if (firstOperand >= secondOperand) {
                    "1"
                } else {
                    "0"
                }
            }

            5 -> {
                return if (firstOperand <= secondOperand) {
                    "1"
                } else {
                    "0"
                }
            }

            6 -> {
                return if (firstOperand * secondOperand >= 1) {
                    "1"
                } else {
                    "0"
                }
            }

            7 -> {
                return if (firstOperand + secondOperand >= 1) {
                    "1"
                } else {
                    "0"
                }
            }
        }

        return "0"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display() {
        val operators = listOf("==", "!=", ">", "<", ">=", "<=", "and", "or")
        val selectedOperator = remember { mutableStateOf(condition) }
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
                    label = { Text("Operand 1", color = White00) },
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
                                    condition = operator
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
                    label = { Text("Operand 2", color = White00) }
                )
            }
            else {
                secondBlockState.value!!.Display()
            }
        }
    }

    private fun setVariableFirst(firstValue: String) {
        _leftValueState.value = firstValue
        leftVariable = firstValue
    }

    private fun setVariableSecond(secondValue: String) {
        _rightValueState.value = secondValue
        rightVariable = secondValue
    }

    fun setFirstBlock(block: CodeBlock) {
        _leftBlockState.value = block
        leftBlock = block
    }

    fun setSecondBlock(block: CodeBlock) {
        _rightBlockState.value = block
        rightBlock = block
    }

    private fun getNormalizedName(name: String, variables: MutableMap<String, Double>): String {
        val regular_name = "(?:[a-zA-Z][-_]*)+".toRegex()
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
        val regular_array = "(?:[a-zA-Z][-_]*)+\\[(?:[a-zA-Z]+[-_]*)+\\]".toRegex()
        val answer = regular_array.matches(value)
        println("checking: ${answer}")
        return answer
    }

    companion object {
        private const val serialVersionUID = 123356L
    }
}