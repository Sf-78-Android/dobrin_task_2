package com.tutorial.calculatorapp.model

import com.tutorial.calculatorapp.`interface`.ICalculator
import com.tutorial.calculatorapp.constants.Constants.DIVIDER
import com.tutorial.calculatorapp.constants.Constants.DOT_ZERO
import com.tutorial.calculatorapp.constants.Constants.FACTORIAL_SIGN
import com.tutorial.calculatorapp.constants.Constants.INITIAL_VALUE
import com.tutorial.calculatorapp.constants.Constants.MINUS
import com.tutorial.calculatorapp.constants.Constants.MULTIPLIER
import com.tutorial.calculatorapp.constants.Constants.PLUS
import com.tutorial.calculatorapp.constants.Constants.REGEX_VALUE
import com.tutorial.calculatorapp.constants.Constants.SQRT_SIGN
import kotlin.math.floor
import kotlin.math.sqrt


class Calculator : ICalculator {
    private var isNegative: Boolean = false

    override fun onEqual(input: String): Double {
        val result: Double
        val regex = REGEX_VALUE.toRegex()
        var operations = input.split(regex).toMutableList()
        operations.removeAll(listOf("", null))
        if (operations[0] == MINUS) {
            operations.removeFirst()
            isNegative = true
        }
        if (operations[operations.size - 1] == MINUS || operations[operations.size - 1] == PLUS ||
            operations[operations.size - 1] == MULTIPLIER || operations[operations.size - 1] == DIVIDER
        ) {
            operations.removeLast()
        }
        while (operations.size > 1) {
            operations =
                when {
                    SQRT_SIGN in operations ->
                        calculate(operations, SQRT_SIGN)
                    FACTORIAL_SIGN in operations ->
                        calculate(operations, FACTORIAL_SIGN)
                    DIVIDER in operations ->
                        calculate(operations, DIVIDER)
                    MULTIPLIER in operations ->
                        calculate(operations, MULTIPLIER)
                    MINUS in operations ->
                        calculate(operations, MINUS)
                    PLUS in operations ->
                        calculate(operations, PLUS)
                    else -> mutableListOf()
                }
        }

        result = operations[0].toDouble()

        if (result > 0) {
            isNegative = false
        }

        return removeZeroAfterDot(result.toString())
    }

    override fun calculate(operations: MutableList<String>, operand: String): MutableList<String> {
        var leftNum: Double = INITIAL_VALUE
        var rightNum: Double = INITIAL_VALUE
        while (operations.contains(operand)) {
            val operandIndex = operations.indexOfFirst { it == operand }
            if (operand != FACTORIAL_SIGN && operand != SQRT_SIGN) {
                leftNum = getLeftNum(operations[operandIndex - 1])
                rightNum = operations[operandIndex + 1].toDouble()
            }

            when (operand) {
                SQRT_SIGN -> operations[operandIndex] =
                    calculateSqrt(operations[operandIndex + 1]).also {
                        operations.removeAt(
                            operandIndex + 1
                        )
                    }
                FACTORIAL_SIGN -> operations[operandIndex - 1] =
                    getFactorial(
                        floor(operations[operandIndex - 1].toDouble()),
                        operations[operandIndex - 1].toDouble()
                    ).also { operations.removeAt(operandIndex) }
                DIVIDER -> operations[operandIndex - 1] =
                    (leftNum / rightNum).toString().also {
                        operations.removeAt(operandIndex)
                            .also { operations.removeAt(operandIndex - 1) }
                    }
                MULTIPLIER -> operations[operandIndex - 1] =
                    (leftNum * rightNum).toString().also {
                        operations.removeAt(operandIndex)
                            .also { operations.removeAt(operandIndex) }
                    }
                PLUS -> operations[operandIndex - 1] =
                    (leftNum + rightNum).toString().also {
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

    private fun getFactorial(value: Double, total: Double): String {
        var currResult = floor(total)
        return if (value <= 1) {
            floor(total).toString()
        } else {
            currResult *= (value - 1)
            getFactorial(value - 1, currResult)
        }
    }

    override fun removeZeroAfterDot(result: String): Double {
        var value = result
        if (result.contains(DOT_ZERO)) {
            value = result.substring(0, result.length - 2)
        }
        return value.toDouble()
    }

    override fun getLeftNum(number: String): Double {
        if (isNegative) {
            return INITIAL_VALUE - number.toDouble()
        }
        return number.toDouble()
    }

    override fun calculateSqrt(value: String): String {
        return sqrt(value.toDouble()).toString()
    }

}