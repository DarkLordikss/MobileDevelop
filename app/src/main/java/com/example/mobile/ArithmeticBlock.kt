package com.example.mobile

class ArithmeticBlock (
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableFirst: String,
    private var variableSecond: String,
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
                } catch (e: NumberFormatException){
                    throw NumberFormatException("ERROR: check your mother bro, i think you don't have one")
                }
            }
        }
        else {
            firstOperand = blockFirst.executeBlock().toDouble()
        }

        if (blockSecond == null) {
            secondOperand = variables[variableSecond]
            if (secondOperand == null) {
                try {
                    secondOperand = variableSecond.toDouble()
                } catch (e: NumberFormatException){
                    throw NumberFormatException("ERROR: check your mother bro, i think you don't have one")
                }
            }
        }
        else {
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
                myAnswer = (firstOperand / secondOperand).toString()
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