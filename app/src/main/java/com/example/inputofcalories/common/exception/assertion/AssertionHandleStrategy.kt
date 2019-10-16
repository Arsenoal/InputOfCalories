package com.example.inputofcalories.common.exception.assertion

interface AssertionHandleStrategy {
    fun execute(t: Throwable)
}