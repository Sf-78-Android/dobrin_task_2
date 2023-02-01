package com.tutorial.calculatorapp.`interface`

interface ICalculator {

    fun onEqual(input: String): Double
    fun calculate(operations: MutableList<String>, operand: String): MutableList<String>
    fun removeZeroAfterDot(result: String): Double
    fun getLeftNum(number: String): Double

}