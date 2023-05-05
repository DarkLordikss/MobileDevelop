package com.example.mobile

class ConditionalBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var leftVariable: String = "",
    private var rightVariable: String = "",
    private var condition: String = "",
    private val leftBlock: CodeBlock? = null,
    private val rightBlock: CodeBlock? = null,
    private val trueNode: CodeBlock? = null,
    private val falseNode: CodeBlock? = null
): CodeBlock(variables)  {
    override fun executeBlock(): String {
        val conditionsArray = mapOf(
            "==" to 0,
            "!=" to 1
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
                if (firstOperand == secondOperand) {
                    if (trueNode != null) {
                        return trueNode.executeBlock()
                    } else {
                        throw Exception("ERROR: empty Conditional Block body")
                    }
                } else {
                    if (falseNode != null) {
                        return falseNode.executeBlock()
                    }
                }
            }

            1 -> {
                if (firstOperand != secondOperand) {
                    if (trueNode != null) {
                        return trueNode.executeBlock()
                    } else {
                        throw Exception("ERROR: empty Conditional Block body")
                    }
                } else {
                    if (falseNode != null) {
                        return falseNode.executeBlock()
                    }
                }
            }
        }

        return "0"
    }
}