package com.tutorial.calculatorapp.model

import android.view.View
import android.widget.Button
import com.tutorial.calculatorapp.`interface`.ICalculator

class Calculator : ICalculator {
    private var lastNumeric: Boolean = false
    private var dotInserted: Boolean = false
    private var isNegative: Boolean = false
    private var hasResult = false



    override fun onEqual(input: String) : Double {
        var result = 0.0
        val regex = "(?<=[-+*/])|(?=[-+*/])".toRegex()
        var operations = input.split(regex).toMutableList()
        operations.removeAll(listOf("", null))
        if (operations[0] == "-") {
            operations.removeFirst()
            isNegative = true
        }
        if (operations[operations.size - 1] == "-" || operations[operations.size - 1] == "+" ||
            operations[operations.size - 1] == "*" || operations[operations.size - 1] == "/") {
            operations.removeLast()
        }
        while (operations.size > 1) {
            if (operations.contains("/")) {
                operations = calculate(operations, "/")
            } else if (operations.contains("*")) {
                operations = calculate(operations, "*")
            } else if (operations.contains("-")) {
                operations = calculate(operations, "-")
            } else if (operations.contains("+")) {
                operations = calculate(operations, "+")
            }
        }

        result = operations[0].toDouble()


        if (result > 0) {
            isNegative = false
        }


        return removeZeroAfterDot(result.toString())

    }




    override fun calculate(operations: MutableList<String>, operand: String): MutableList<String> {
        while (operations.contains(operand)) {
            val operandIndex = operations.indexOfFirst { it == operand }
            val leftNum = getLeftNum(operations[operandIndex - 1])
            val rightNum = operations[operandIndex + 1].toDouble()
            when (operand) {
                "/" -> operations[operandIndex - 1] = (leftNum / rightNum).toString().also {
                    operations.removeAt(operandIndex)
                        .also { operations.removeAt(operandIndex - 1) }
                }
                "*" -> operations[operandIndex - 1] = (leftNum * rightNum).toString().also {
                    operations.removeAt(operandIndex)
                        .also { operations.removeAt(operandIndex) }
                }
                "+" -> operations[operandIndex - 1] = (leftNum + rightNum).toString().also {
                    operations.removeAt(operandIndex)
                        .also { operations.removeAt(operandIndex) }
                }
                else -> operations[operandIndex - 1] = (leftNum - rightNum).toString().also {
                    operations.removeAt(operandIndex)
                        .also { operations.removeAt(operandIndex) }
                }
            }
        }
        return operations
    }

    override fun removeZeroAfterDot(result: String): Double {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value.toDouble()
    }

    override fun getLeftNum(number: String): Double {
        if (isNegative) {
            return 0.0 - number.toDouble()
        }
        return number.toDouble()
    }


}