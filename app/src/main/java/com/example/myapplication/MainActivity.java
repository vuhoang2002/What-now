package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvInput, tvResult;
    private StringBuilder input = new StringBuilder();
    private ArrayList<String> operations = new ArrayList<>();
    private String lastOperation = "";
    private boolean isResultDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInput = findViewById(R.id.tvInput);
        tvResult = findViewById(R.id.tvResult);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String buttonText = b.getText().toString();

                if (buttonText.equals("C")) {
                    input.setLength(0);
                    operations.clear();
                    lastOperation = "";
                    tvInput.setText("0");
                    tvResult.setText("0");
                } else if (buttonText.equals("Del")) {
                    if (input.length() > 0) {
                        input.deleteCharAt(input.length() - 1);
                    }
                    tvInput.setText(input.toString());
                } else if (buttonText.equals("=")) {
                    if (input.length() > 0) {
                        operations.add(input.toString());
                    }
                    calculateResult();
                    input.setLength(0);
                    isResultDisplayed = true;
                } else if (isOperator(buttonText)) {
                    if (input.length() > 0) {
                        operations.add(input.toString());
                        operations.add(buttonText);
                        input.setLength(0);
                    } else if (lastOperation.length() > 0 && isOperator(lastOperation)) {
                        operations.set(operations.size() - 1, buttonText);
                    }
                } else {
                    if (isResultDisplayed) {
                        input.setLength(0);
                        isResultDisplayed = false;
                    }
                    input.append(buttonText);
                }

                lastOperation = buttonText;
                if (!buttonText.equals("=")) {
                    tvInput.setText(input.toString());
                }
            }
        };

        int[] buttonIds = new int[]{
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnEquals, R.id.btnClear, R.id.btnDelete, R.id.btnDot
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private boolean isOperator(String buttonText) {
        return buttonText.equals("+") || buttonText.equals("-") || buttonText.equals("*") || buttonText.equals("/");
    }

    private void calculateResult() {
        double result = 0;
        String operation = "";
        for (String op : operations) {
            if (isOperator(op)) {
                operation = op;
            } else {
                double num = Double.parseDouble(op);
                switch (operation) {
                    case "":
                        result = num;
                        break;
                    case "+":
                        result += num;
                        break;
                    case "-":
                        result -=num;
                }
            }
        }
    }
}