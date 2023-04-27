package com.example.mobile

abstract class CodeBlock (var variables: MutableMap<String, String>) {
    abstract fun executeBlock()
}