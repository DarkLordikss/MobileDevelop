package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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

class ArithmeticBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableFirst: String = "",
    private var variableSecond: String = "",
    private var myOperator: String = "",
    private val blockFirst: CodeBlock? = null,
    private val blockSecond: CodeBlock? = null
): CodeBlock(variables), Serializable {
    private val operatorsArray = mapOf(
        "+" to 0,
        "-" to 1,
        "*" to 2,
        "/" to 3,
        "^" to 4,
        "%" to 5
    )

    private var _firstState = mutableStateOf(variableFirst)
    private var _secondState = mutableStateOf(variableSecond)

    private val firstState: State<String> = _firstState
    private val secondState: State<String> = _secondState

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
            blockFirst.variables = variables
            firstOperand = blockFirst.executeBlock().toDouble()
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
            blockSecond.variables = variables
            secondOperand = blockSecond.executeBlock().toDouble()
        }

        var myAnswer = ""

        when (operId) {
            0 -> {
                myAnswer = (firstOperand + secondOperand).toString()
            }
            1 -> {
                myAnswer = (firstOperand - secondOperand).toString()
            }
            2 -> {
                myAnswer = (firstOperand * secondOperand).toString()
            }
            3 -> {
                if (secondOperand!! == 0.0){
                    throw Exception(
                        "ERROR: Great Xi is don`t happy." +
                                " You tried to divide $firstOperand to zero"
                    )
                }
            }
            4 -> {
                myAnswer = (Math.pow(firstOperand, secondOperand)).toString()
            }
            5 -> {
                myAnswer = (firstOperand % secondOperand).toString()
            }
        }

        return myAnswer
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display() {
        val operators = listOf("+", "-", "*", "/", "^", "%")
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
            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = firstState.value,
                onValueChange = { newValue ->
                    setVariableFirst(newValue)
                },
                label = { Text("Operand 1", color = White00) },
            )
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
            OutlinedTextField(
                modifier = Modifier.width(100.dp),
                value = secondState.value,
                onValueChange = { newValue ->
                    setVariableSecond(newValue)
                },
                label = { Text("Operand 2", color = White00) }
            )
        }
    }

    private fun setVariableFirst(firstValue: String) {
        _firstState.value = firstValue
        variableFirst = firstValue
    }

    private fun setVariableSecond(secondValue: String) {
        _secondState.value = secondValue
        variableSecond = secondValue
    }

    companion object {
        private const val serialVersionUID = 123455L
    }
}