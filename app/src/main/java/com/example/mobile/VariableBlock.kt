package com.example.mobile

class VariableBlock(
    variables: MutableMap<String, Int> = mutableMapOf(),
    private var variableName: String = "",
    private var variableValueName: String = "",
    private var variableValue: Int = 0
) : CodeBlock(variables) {
    override fun executeBlock() {
        if (variableValueName == "") {
            variables[variableName] = variableValue
        }
        else {
            if (variables[variableValueName] != null) {
                variables[variableName] = variables[variableValueName]!!
            }
        }
    }

    fun setVariableName(name: String) {
        variableName = name
    }

    fun setVariableValueName(name: String) {
        variableValueName = name
    }

    fun setVariableValue(value: Int) {
        variableValue = value
    }
}