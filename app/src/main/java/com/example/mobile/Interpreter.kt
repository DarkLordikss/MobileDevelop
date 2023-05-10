package com.example.mobile

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun interpretProgram(blockList: MutableState<List<CodeBlock>>,
                     textList: MutableState<List<String>>,
                     variables: MutableMap<String, Double> = mutableMapOf<String, Double>(),
                     onComplete: () -> Unit
) {

    GlobalScope.launch {
        try {
            blockList.value.forEach { block ->
                block.variables = variables
                block.executeBlock()
            }

            //For TEST ONLY
//            variables.forEach { (key, value) ->
//                addTextToList(textList = textList, newText = "VARIABLE $key = $value")
//                Thread.sleep(100)
//            }
        }
        catch (e: Exception) {
            addTextToList(textList = textList, newText = e.message!!)
        }
        finally {
            addTextToList(textList = textList, newText = "| FINISHED! |")
            onComplete()
        }
    }
}
