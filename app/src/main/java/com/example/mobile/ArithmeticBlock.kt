package com.example.mobile

class ArithmeticBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableFirst: String = "",
    private var variableSecond: String = "",
    private var myOperator: String = "",
    private val blockFirst: CodeBlock? = null,
    private val blockSecond: CodeBlock? = null
): CodeBlock(variables) {
    override fun executeBlock(): String {
        val operatorsArray = mapOf(
            "+" to 0,
            "-" to 1,
            "*" to 2,
            "/" to 3,
            "^" to 4,
            "%" to 5
        )
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
}