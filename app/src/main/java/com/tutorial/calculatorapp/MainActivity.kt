package com.tutorial.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tutorial.calculatorapp.constants.Constants
import com.tutorial.calculatorapp.model.Calculator

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var dotInserted: Boolean = false
    private var isNegative: Boolean = false
    private var hasResult = false
    private val myCalculator = Calculator()

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
            tvInput?.append(Constants.DOT)
            dotInserted = true
            lastNumeric = false
        } else if (hasResult) {
            onClear(view)
            tvInput?.append(Constants.ZERO_DOT)
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

        val operations = tvInput?.text.toString()

        val result = myCalculator.onEqual(operations)

        if (result > 0) {
            isNegative = false
        }
        tvInput?.text = result.toString()

        hasResult = true
    }

}