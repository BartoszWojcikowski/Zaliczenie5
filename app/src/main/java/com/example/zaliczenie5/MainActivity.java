package com.example.zaliczenie5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView operationTextView, numberTextView;
    private double num1 = 0, num2 = 0;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operationTextView = findViewById(R.id.operationTextView);
        numberTextView = findViewById(R.id.numberTextView);
    }

    public void seven(View view) {
        appendNumber("7");
    }

    public void eight(View view) {
        appendNumber("8");
    }

    public void nine(View view) {
        appendNumber("9");
    }

    public void addition(View view) {
        performOperation("+");
    }

    public void four(View view) {
        appendNumber("4");
    }

    public void five(View view) {
        appendNumber("5");
    }

    public void six(View view) {
        appendNumber("6");
    }

    public void subtraction(View view) {
        performOperation("-");
    }

    public void one(View view) {
        appendNumber("1");
    }

    public void two(View view) {
        appendNumber("2");
    }

    public void three(View view) {
        appendNumber("3");
    }

    public void multiplication(View view) {
        performOperation("*");
    }

    public void zero(View view) {
        appendNumber("0");
    }

    public void dot(View view) {
        String currentNumber = numberTextView.getText().toString();
        if (!currentNumber.contains(".")) {
            numberTextView.append(".");
        }
    }

    public void division(View view) {
        performOperation("/");
    }

    public void clear(View view) {
        clearCalculator();
    }

    public void equal(View view) {
        calculateResult();
    }

    private void appendNumber(String number) {
        String currentNumber = numberTextView.getText().toString();
        numberTextView.setText(currentNumber.equals("0") ? number : currentNumber + number);
    }

    @SuppressLint("SetTextI18n")
    private void performOperation(String newOperator) {
        String currentNumber = numberTextView.getText().toString();
        if (!currentNumber.isEmpty()) {
            if (!operator.isEmpty()) {
                calculateResult();
            }
            num1 = Double.parseDouble(currentNumber);
            operator = newOperator;
            operationTextView.setText(num1 + " " + operator);
            numberTextView.setText("0");
        }
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult() {
        String currentNumber = numberTextView.getText().toString();
        if (!currentNumber.isEmpty()) {
            num2 = Double.parseDouble(currentNumber);
            double result = 0;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        // Handle division by zero
                        clearCalculator();
                        operationTextView.setText("Error");
                        return;
                    }
                    break;
            }
            clearCalculator();
            operationTextView.setText("");
            numberTextView.setText(String.valueOf(result));
        }
    }

    private void clearCalculator() {
        num1 = 0;
        num2 = 0;
        operator = "";
        numberTextView.setText("0");
    }
}
