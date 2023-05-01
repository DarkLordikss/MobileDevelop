package com.example.mobile

abstract class CodeBlock (var variables: MutableMap<String, Double>) {
    abstract fun executeBlock() : String
}