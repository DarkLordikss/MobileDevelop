package com.example.mobile

class VariableBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var variableName: String = "",
    private var variableValue: String = "",
) : CodeBlock(variables) {
    override fun executeBlock(): String {
        if (variables[variableValue] == null) {
            try {
                variables[variableName] = variableValue.toDouble()
            }
            catch (e: NumberFormatException) {
                throw NumberFormatException("ERROR: Variable $variableValue is not exist!")
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
}