package com.example.mobile

class ConditionalBlock(
    variables: MutableMap<String, Double> = mutableMapOf(),
    private var booleanBlock: BooleanBlock,
    private val trueNode: CodeBlock? = null,
    private val falseNode: CodeBlock? = null
): CodeBlock(variables)  {
    override fun executeBlock(): String {
        var condition = booleanBlock.executeBlock()
        if (condition.toDouble() > 0){
            if (trueNode != null){
                trueNode.executeBlock()
            }else{
                throw Exception("ERROR: empty Conditional Block body")
            }
            if (falseNode != null){
                falseNode.executeBlock()
            }
        }

        return "0"
    }
}