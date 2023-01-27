package com.tutorial.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var dotInserted: Boolean = false
    private var isNegative: Boolean = false
    private var hasResult = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        if (hasResult) {
            onClear(view)
        }
        tvInput?.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        dotInserted = false
        lastNumeric = false
        hasResult = false
        isNegative = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !dotInserted && !hasResult) {
            tvInput?.append(".")
            dotInserted = true
            lastNumeric = false
        } else if (hasResult) {
            onClear(view)
            tvInput?.append("0.")
            dotInserted = true
            lastNumeric = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                dotInserted = false
                hasResult = false
            }
        }
    }

    fun onEqual(view: View) {
        var result = 0.0
        val regex = "(?<=[-+*/])|(?=[-+*/])".toRegex()
        var operations = tvInput?.text.toString().split(regex).toMutableList()
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


        tvInput?.text = removeZeroAfterDot(result.toString())

        hasResult = true
    }

    private fun calculate(operations: MutableList<String>, operand: String): MutableList<String> {
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
                    operations.remove(operations[operandIndex])
                        .also { operations.removeAt(operandIndex) }
                }
            }
        }
        return operations
    }

    private fun getLeftNum(number: String): Double {
        if (isNegative) {
            return 0.0 - number.toDouble()
        }
        return number.toDouble()
    }


    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}