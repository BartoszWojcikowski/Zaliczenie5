package com.example.zaliczenie5;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import com.example.zaliczenie5.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // defining binding for the Main Activity -> Activity+Main+Binding naming
    private ActivityMainBinding binding;
    //define initial states
    private String currentActionState = "";
    private String currentNumberState = "";
    private String currentSignState = "";
    private String previousSignState = "";
    private String currentEquationState = "";

    // defining Lists for keeping numbers and signs
    List<String> numbers = new ArrayList<>();
    List<String> operators = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_main);
        // set the binding instead of Root view
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // assign functions accepting buttons and further entries as parameters
        setNumberClickListener(binding.button9, "9");
        setNumberClickListener(binding.button8, "8");
        setNumberClickListener(binding.button7, "7");
        setNumberClickListener(binding.button6, "6");
        setNumberClickListener(binding.button5, "5");
        setNumberClickListener(binding.button4, "4");
        setNumberClickListener(binding.button3, "3");
        setNumberClickListener(binding.button2, "2");
        setNumberClickListener(binding.button1, "1");
        setNumberClickListener(binding.button0, "0");
        setSignClickListener(binding.buttonplus, "+");
        setSignClickListener(binding.buttonminus, "-");
        setSignClickListener(binding.buttonx, "x");
        setSignClickListener(binding.buttondivide, "/");
        setEqualSignClickListener(binding.buttonequal, "=");
        // passing a clear button as parameter
        buttonCleared(binding.buttonc);
    }

    @SuppressLint("SetTextI18n")
    private void setNumberClickListener(Button view, String number) {

        view.setOnClickListener(v -> {

        // if the value is 0,
            if (getNumberState().equals("0")){
                //if the user enters a 0
                if (number.equals("0")){
                    //if the number is 0, setup 0 back, as cannot be multiple 00000
                    setNumberState("0");
                    setEquationState("0");
                    // if the user press 0 it adds up 0 to the array
                } else {
                    // else changes the number to pressed for both the equation and result
                    setNumberState(number);
                    setEquationState(number);
                }
//if the first number is not 0, append the to the current value the pressed number
            } else if (getActionState().equals("sign")) {
                setNumberState(number);
                setEquationState(getEquationState() + number);

            }
            else {
                setNumberState(getNumberState() + number);
                setEquationState(getEquationState() + number);

            }
            // apply . to the number
            addFloat();
            setActionState("number");
        });
    }
    private void buttonCleared(Button buttonc) {
        //set the text to 0
        buttonc.setOnClickListener(v -> {
            setNumberState("0");
            setActionState("");
            setSignState("");
            // clear the array of numbers and operators
            numbers.clear();
            operators.clear();
            setEquationState("");
        });
    }

    @SuppressLint("SetTextI18n")
    private void setSignClickListener(Button view, String sign) {

        view.setOnClickListener(v -> {
            if (!getActionState().equals("sign")){
                setSignState(sign);
                setEquationState(getEquationState() + sign);

                // adding the number value to to the list
                numbers.add(getNumberState());
                // adding the sign into array
                operators.add(getSignState());
                setActionState("sign");
            }
        });


    }

    private void setEqualSignClickListener(Button view, String sign) {

        view.setOnClickListener(v -> {
            if (!getActionState().equals("equalsign") && !getActionState().equals("")) {
                //setting the state of the sign to the pressed sign which is equal sign
                setSignState(sign);
                //add the last number from the entry to the array
                numbers.add(getNumberState());
                //applying the state of the action to the equalsign

                double result = Double.parseDouble(numbers.get(0));
                for (int i = 0; i < numbers.size() - 1; i++) {
                    // check if the operation is 1+, then need to adjust a chain for
                    if (i > 0){
                        //tracing the previous operator, this will need to multiply and division
                        setPreviousSignState(operators.get(i-1));
                    }
                    String operator = operators.get(i);
                    //  retrieving the next nr
                    double nextNumber = Double.parseDouble(numbers.get(i + 1));
                    // switch statement to compare the signs, for multiply and divide - nested statement, as calculation order is changing
                    switch (operator) {
                        case "+":
                            result += nextNumber;
                            break;
                        case "-":
                            result -= nextNumber;
                            break;
                        case "x":
                            // if its multiply, checking the previous sign, and reverse the SUM or SUBTRACTION
                            switch (getPreviousSignState()) {
                                case "":
                                    result *= nextNumber;
                                    break;
                                case "+":
                                // if the previous sign was +, SUBTRACT THE added value, and update the calculation
                                    result -= Double.parseDouble(numbers.get(i));
                                    result = result + (Double.parseDouble(numbers.get(i)) * nextNumber);
                                    //4 + 4-3+9x3+9+2 = 43 +
                                    //4 + 4-3+9/3+9+2 = 19 +
                                    break;
                                case "-":
                                    // if the previous sign was -, ADD THE added value, and update the calculation

                                    result += Double.parseDouble(numbers.get(i));
                                    result = result - (Double.parseDouble(numbers.get(i)) * nextNumber);
                                    break;
                            }
                            break;
                        case "/":
                            // DIVISION by 0 is not allowed, error displays, and all values are getting dropped

                            if (getNumberState().equals("0")) {
                                setNumberState("error occured");
                                numbers.clear();
                                operators.clear();
                                setActionState("equalsign");
                                return;
                            } else {
                                // if its divison not by 0, checking the previous sign, and reverse the SUM or SUBTRACTION

                                switch (getPreviousSignState()) {
                                    case "":
                                        result /= nextNumber;
                                        // 8 - 3 = 5 - 27 = - 22 + 11 = -11
                                        //4 + 4-3-9x3+9+2 = -21
                                        //4 + 4-3-9/3+9+2 = 13 +
                                        break;
                                    case "+":
                                        // if the previous sign was +, SUBTRACT THE added value, and update the calculation

                                        result -= Double.parseDouble(numbers.get(i));
                                        result = result + (Double.parseDouble(numbers.get(i)) / nextNumber);
                                        break;
                                    case "-":
                                        // if the previous sign was -, ADD THE added value, and update the calculation

                                        result += Double.parseDouble(numbers.get(i));
                                        result = result - (Double.parseDouble(numbers.get(i)) / nextNumber);
                                        break;
                                }
                            }
                            break;
                    }
                }
                // assign the result value, and displaying, clearing the number and and operator, assigning the result value to both - result and equation displaying in calculator
                setNumberState(String.valueOf(result));
                numbers.clear();
                operators.clear();
                setEquationState(String.valueOf(result));
                setActionState("equalsign");
            }
        });

        ;}

    @SuppressLint("SetTextI18n")
    private void addFloat() {
        binding.buttondot.setOnClickListener(v -> {
            //if number does not contain and if the current action is not pressed as sign
            if (!getNumberState().contains(".") && !getActionState().equals("sign")){
                setNumberState(getNumberState() + ".");
                setEquationState(getEquationState() + ".");
            }
        });
    }

    // Method to get the current state
    private String getActionState() {
        return currentActionState;
    }
    private String getNumberState(){
        return currentNumberState;
    }
    private String getSignState(){
        return currentSignState;
    }

    private String getEquationState(){
        return currentEquationState;
    }

    private String getPreviousSignState(){
        return previousSignState;
    }

    // methods to set and change the default states
    private void setActionState(String newState) {
        currentActionState = newState;

    }
    private void setNumberState(String newState) {
        currentNumberState = newState;
        binding.tvresultshow.setText(currentNumberState);
    }
    private void setSignState(String newState) {
        currentSignState = newState;
        binding.tvsign.setText(currentSignState);
    }
    private void setEquationState(String newState){
        currentEquationState = newState;
        binding.tvequation.setText(currentEquationState);
    }
    private void setPreviousSignState(String newState){
        previousSignState = newState;
    }

}

