package com.example.mobile

abstract class CodeBlock (var variables: MutableMap<String, Int>) {
    abstract fun executeBlock()
}