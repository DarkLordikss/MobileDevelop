package com.example.mobile

class BooleanBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var leftVariable: String = "",
    private var rightVariable: String = "",
    private var condition: String = "",
    private val leftBlock: CodeBlock? = null,
    private val rightBlock: CodeBlock? = null
): CodeBlock(variables)  {
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
            leftBlock.variables = variables
            firstOperand = leftBlock.executeBlock().toDouble()
        }

        if (rightBlock == null) {
            secondOperand = variables[rightVariable]
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
            rightBlock.variables = variables
            secondOperand = rightBlock.executeBlock().toDouble()
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
}