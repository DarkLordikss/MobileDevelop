package com.example.mobile

class ArithmeticBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableFirst: String = "",
    private var variableSecond: String = "",
    private var myOperator: Int,
    private val blockFirst: CodeBlock? = null,
    private val blockSecond: CodeBlock? = null
): CodeBlock(variables) {
    override fun executeBlock(): String {
        var firstOperand: Double?
        var secondOperand: Double?

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

        when (myOperator) {
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
                try {
                    myAnswer = (firstOperand / secondOperand).toString()
                }
                catch (e: Exception) {
                    throw Exception(
                        "ERROR: Great Xi is don`t happy." +
                        " You tried to divide $firstOperand to zero")
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
}