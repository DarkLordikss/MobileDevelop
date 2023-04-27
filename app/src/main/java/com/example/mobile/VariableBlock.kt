package com.example.mobile

class VariableBlock(
    variables: MutableMap<String, String> = mutableMapOf(),
    private var variableName: String = "",
    private var variableValue: String = "",
) : CodeBlock(variables) {
    override fun executeBlock() {
        if (variables[variableValue] == null) {
            try {
                variables[variableName] = variableValue.toDouble().toString()
            }
            catch (e: NumberFormatException) {
                throw NumberFormatException("ERROR: Variable $variableValue is not exist!")
            }
        }
        else {
            variables[variableName] = variables[variableValue]!!
        }
    }

    fun setVariableName(name: String) {
        variableName = name
    }

    fun setVariableValue(value: String) {
        variableValue = value
    }
}