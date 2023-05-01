package com.example.mobile

class VariableBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableName: String = "",
    private var variableValue: String = "",
    private var blockValue: CodeBlock? = null
) : CodeBlock(variables) {
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

    fun setVariableName(name: String) {
        variableName = name
    }

    fun setVariableValue(value: String) {
        variableValue = value
    }

    fun setBlockValue(block: CodeBlock) {
        blockValue = block
    }
}