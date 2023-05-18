package com.example.mobile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

abstract class CodeBlock (
    var variables: MutableMap<String, Double>,
) {
    abstract fun executeBlock() : String

    @Composable
    abstract fun Display()
}