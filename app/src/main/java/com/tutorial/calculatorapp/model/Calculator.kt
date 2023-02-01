package com.tutorial.calculatorapp.model

import com.tutorial.calculatorapp.`interface`.ICalculator
import com.tutorial.calculatorapp.constants.Constants

class Calculator : ICalculator {
    private var isNegative: Boolean = false


    override fun onEqual(input: String) : Double {
        val result: Double
        val regex = Constants.REGEX_VALUE.toRegex()
        var operations = input.split(regex).toMutableList()
        operations.removeAll(listOf("", null))
        if (operations[0] == Constants.MINUS) {
            operations.removeFirst()
            isNegative = true
        }
        if (operations[operations.size - 1] == Constants.MINUS || operations[operations.size - 1] == Constants.PLUS ||
            operations[operations.size - 1] == Constants.MULTIPLIER || operations[operations.size - 1] == Constants.DIVIDER) {
            operations.removeLast()
        }
        while (operations.size > 1) {
            if (operations.contains(Constants.DIVIDER)) {
                operations = calculate(operations, Constants.DIVIDER)
            } else if (operations.contains(Constants.MULTIPLIER)) {
                operations = calculate(operations, Constants.MULTIPLIER)
            } else if (operations.contains(Constants.MINUS)) {
                operations = calculate(operations, Constants.MINUS)
            } else if (operations.contains(Constants.PLUS)) {
                operations = calculate(operations, Constants.PLUS)
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
                Constants.DIVIDER -> operations[operandIndex - 1] = (leftNum / rightNum).toString().also {
                    operations.removeAt(operandIndex)
                        .also { operations.removeAt(operandIndex - 1) }
                }
                Constants.MULTIPLIER -> operations[operandIndex - 1] = (leftNum * rightNum).toString().also {
                    operations.removeAt(operandIndex)
                        .also { operations.removeAt(operandIndex) }
                }
                Constants.PLUS-> operations[operandIndex - 1] = (leftNum + rightNum).toString().also {
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
        if (result.contains(Constants.DOT_ZERO)) {
            value = result.substring(0, result.length - 2)
        }
        return value.toDouble()
    }

    override fun getLeftNum(number: String): Double {
        if (isNegative) {
            return Constants.INITIAL_VALUE- number.toDouble()
        }
        return number.toDouble()
    }


}