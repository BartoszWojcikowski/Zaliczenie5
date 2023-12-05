package com.example.zaliczenie5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView operationTextView, numberTextView;

    String operation = "";

    Double result = null;
    Button zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, dotButton, equalButton, additionButton, subtractionButton, multiplicationButton, divisionButton, clearButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();

        zeroButton = findViewById(R.id.zeroButton);
        oneButton = findViewById(R.id.oneButton);
        twoButton = findViewById(R.id.twoButton);
        threeButton = findViewById(R.id.threeButton);
        fourButton = findViewById(R.id.fourButton);
        fiveButton = findViewById(R.id.fiveButton);
        sixButton = findViewById(R.id.sixButton);
        sevenButton = findViewById(R.id.sevenButton);
        eightButton = findViewById(R.id.eightButton);
        nineButton = findViewById(R.id.nineButton);
        additionButton = findViewById(R.id.additionButton);
        subtractionButton = findViewById(R.id.subtractionButton);
        multiplicationButton = findViewById(R.id.multiplicationButton);
        divisionButton = findViewById(R.id.divisionButton);
        dotButton = findViewById(R.id.dotButton);
        equalButton = findViewById(R.id.equalButton);
        clearButton = findViewById(R.id.clearButton);

    }
    private void initTextViews() {
        operationTextView = findViewById(R.id.operationTextView);
        numberTextView = findViewById(R.id.numberTextView);
    }
    private void setOperation(String value) {
        operation += value;
        operationTextView.setText(operation);
    }
    public void clear(View view) {
        operationTextView.setText("");
        numberTextView.setText("");
        operation = "";
        result = null;
    }
    public void seven(View view) {
        setOperation("7");
    }
    public void eight(View view) {
        setOperation("8");
    }
    public void nine(View view) {
        setOperation("9");
    }
    public void addition(View view) {
        setOperation("+");
    }
    public void four(View view) {
        setOperation("4");
    }
    public void five(View view) {
        setOperation("5");
    }
    public void six(View view) {
        setOperation("6");
    }

    public void subtraction(View view) {
        setOperation("-");
    }
    public void one(View view) {
        setOperation("1");
    }
    public void two(View view) {
        setOperation("2");
    }
    public void three(View view) {
        setOperation("3");
    }
    public void multiplication(View view) {
        setOperation("*");
    }
    public void zero(View view) {
        setOperation("0");
    }
    public void dot(View view) {
        setOperation(".");
    }
    public void division(View view) {
        setOperation("/");
    }
    public void equal(View view) {
        try {
            // Evaluate the mathematical expression
            result = evaluateExpression(operation);
            // Display the result
            numberTextView.setText(String.valueOf(result));
        } catch (ArithmeticException e) {
            // Handle division by zero or other arithmetic errors
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Double evaluateExpression(String operation) {
        // Split the expression into operands and operators
        String[] operationParts = operation.split("(?<=[-+*/])|(?=[-+*/])");
        // Iterate over the array of operators and operands performing one operation at a time
        for (int i = 0; i < operationParts.length; i++) {
            // If the current element is an operator
            if (operationParts[i].matches("[-+*/]")) {
                // Get the left operand
                Double leftOperand = Double.parseDouble(operationParts[i - 1]);
                // Get the right operand
                Double rightOperand = Double.parseDouble(operationParts[i + 1]);
                // Get the operator
                String operator = operationParts[i];
                // Perform the operation
                Double result = performOperation(leftOperand, rightOperand, operator);
                // Replace the left operand with the result
                operationParts[i - 1] = String.valueOf(result);
                // Shift the remaining elements to the left
                for (int j = i; j < operationParts.length - 2; j++) {
                    operationParts[j] = operationParts[j + 2];
                }
                // Replace the last two elements with null
                operationParts[operationParts.length - 2] = null;
                operationParts[operationParts.length - 1] = null;
                // Decrement the counter
                i--;
            }
        }
        // Return the result
        return Double.parseDouble(operationParts[0]);

    }

    private Double performOperation(Double leftOperand, Double rightOperand, String operator) {
        // Perform the operation
        switch (operator) {
            case "+":
                return leftOperand + rightOperand;
            case "-":
                return leftOperand - rightOperand;
            case "*":
                return leftOperand * rightOperand;
            case "/":
                if (rightOperand == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                return leftOperand / rightOperand;
            default:
                return 0.0;
        }
    }
    }