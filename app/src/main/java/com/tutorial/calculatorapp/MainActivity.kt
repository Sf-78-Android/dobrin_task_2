package com.tutorial.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    private var dotInserted: Boolean = false
    var isNegative: Boolean = false
    var hasResult = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        if (hasResult){
            onClear(view)
        }
        tvInput?.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        dotInserted = false
        lastNumeric = false
        hasResult=false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !dotInserted && !hasResult) {
            tvInput?.append(".")
            dotInserted = true
            lastNumeric = false
        } else if(hasResult){
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
        val operations = tvInput?.text.toString().split(regex).toMutableList()
        operations.removeAll(listOf("", null))
        if (operations[0] == "-") {
            operations.removeFirst()
            isNegative = true
        }
        val numbers = LinkedList<Double>()
        val operators = LinkedList<String>()

        for (i in 0 until operations.size) {
            try {
                numbers.add(operations[i].toDouble())
            } catch (e: java.lang.NumberFormatException) {
                operators.add(operations[i])
            }
        }

        if (!numbers.isEmpty()) {
            result = numbers.pop()
            if (isNegative) {
                result = 0 - result
            }
        }
        while (!numbers.isEmpty()) {
            if (!operators.isEmpty()) {
                when (operators.pop()) {
                    "-" -> result -= numbers.pop()
                    "+" -> result += numbers.pop()
                    "/" -> result /= numbers.pop()
                    "*" -> result *= numbers.pop()
                }
            }
        }
        if (result > 0) {
            isNegative = false
        }
        tvInput?.text = removeZeroAfterDot(result.toString())

        hasResult = true
    }


    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}