package com.example.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstNumber: EditText
    private lateinit var etSecondNumber: EditText
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etFirstNumber = findViewById(R.id.et_first_number)
        etSecondNumber = findViewById(R.id.et_second_number)
        tvResult = findViewById(R.id.tv_result)

        findViewById<Button>(R.id.btn_add).setOnClickListener { calculate('+') }
        findViewById<Button>(R.id.btn_subtract).setOnClickListener { calculate('-') }
        findViewById<Button>(R.id.btn_multiply).setOnClickListener { calculate('*') }
        findViewById<Button>(R.id.btn_divide).setOnClickListener { calculate('/') }
        findViewById<Button>(R.id.btn_clear).setOnClickListener { clearFields() }
    }

    private fun calculate(operation: Char) {
        val num1String = etFirstNumber.text.toString()
        val num2String = etSecondNumber.text.toString()

        if (num1String.isEmpty() || num2String.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = num1String.toDoubleOrNull()
        val num2 = num2String.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show()
            return
        }

        val symbol = when (operation) {
            '+' -> '+'
            '-' -> '-'
            '*' -> '×'
            '/' -> '÷'
            else -> ' '
        }

        val result = when (operation) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> {
                if (num2 == 0.0) {
                    tvResult.text = getString(R.string.error_divide_by_zero)
                    return
                }
                num1 / num2
            }
            else -> 0.0
        }

        tvResult.text = getString(R.string.result_equation, num1String, symbol, num2String, result.toString())
    }

    private fun clearFields() {
        etFirstNumber.text.clear()
        etSecondNumber.text.clear()
        tvResult.text = getString(R.string.result_initial)
    }
}