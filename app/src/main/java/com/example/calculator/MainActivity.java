package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.input);
        display.setShowSoftInputOnFocus(false);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getString(R.string.display).equals(display.getText().toString())) {
                    display.setText("");
                }
            }
        });

        Button btnHome = (Button) findViewById(R.id.btnHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Home.class));
            }
        });
    }

    private void updateText(String strToAdd){
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        if(getString(R.string.display).equals(display.getText().toString())){
            display.setText(strToAdd);
            display.setSelection(cursorPos + 1);
        } else {
            display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
            display.setSelection(cursorPos + 1);
        }
    }

    public void zeroBTN(View view){
        updateText("0");
    }

    public void oneBTN(View view){
        updateText("1");
    }

    public void twoBTN(View view){
        updateText("2");
    }

    public void threeBTN(View view){
        updateText("3");
    }

    public void fourBTN(View view){
        updateText("4");
    }

    public void fiveBTN(View view){
        updateText("5");
    }

    public void sixBTN(View view){
        updateText("6");
    }

    public void sevenBTN(View view){
        updateText("7");
    }

    public void eightBTN(View view){
        updateText("8");
    }

    public void nineBTN(View view){
        updateText("9");
    }

    public void divideBTN(View view){
        replaceLastOperator("÷");
    }

    public void multiplyBTN(View view){
        replaceLastOperator("×");
    }

    public void substractBTN(View view){
        replaceLastOperator("-");
    }

    public void addBTN(View view){
        replaceLastOperator("+");
    }

    public void clearBTN(View view){
        display.setText("");
    }

    public void parenthesisBTN(View view){
        int cursorPos = display.getSelectionStart();
        int openPar = 0;
        int closedPar = 0;
        int textLen = display.getText().length();

        for(int i = 0; i < cursorPos; i++){
            if(display.getText().toString().substring(i, i+1).equals("(")){
                openPar += 1;
            }
            if(display.getText().toString().substring(i, i+1).equals(")")){
                closedPar += 1;
            }
        }

        if(openPar == closedPar || display.getText().toString().substring(textLen-1, textLen).equals("(")){
            updateText("(");
        } else if(closedPar < openPar && !display.getText().toString().substring(textLen-1, textLen).equals("(")){
            updateText(")");
        }
        display.setSelection(cursorPos + 1);
    }

    public void exponentBTN(View view){
        replaceLastOperator("^");
    }

    public void plusMinusBTN(View view){
        String currentText = display.getText().toString();
        int cursorPos = display.getSelectionStart();

        // If the cursor is at the beginning and there's no negative sign, add it
        if (cursorPos == 0 && !currentText.startsWith("-")) {
            updateText("-");
            display.setSelection(cursorPos + 1);
        }
        // If the first character is a negative sign, remove it
        else if (currentText.startsWith("-")) {
            display.setText(currentText.substring(1));
            display.setSelection(cursorPos - 1);
        }
    }

    public void pointBTN(View view){
        String currentText = display.getText().toString();
        if (!isLastCharDecimal() && !currentText.endsWith(")")) {
            updateText(".");
        }
    }

    public void equalBTN(View view){
        String userExp = display.getText().toString();

        userExp = userExp.replaceAll("÷", "/");
        userExp = userExp.replaceAll("×", "*");

        Expression exp = new Expression(userExp);
        String result = String.valueOf(exp.calculate());
        display.setText(result);
        display.setSelection(result.length());

    }

    public void backSpaceBTN(View view){
        int cursorPos = display.getSelectionStart();
        int textLen = display.getText().length();

        if(cursorPos != 0 && textLen != 0){
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos - 1);
        }
    }

    private boolean isLastCharOperator() {
        String currentText = display.getText().toString();
        if (currentText.isEmpty()) {
            return false;  // No characters entered yet.
        }
        char lastChar = currentText.charAt(currentText.length() - 1);
        return lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == '÷' || lastChar == '^';
    }

    private void replaceLastOperator(String newOperator) {
        String currentText = display.getText().toString();
        if (isLastCharOperator()) {
            // Remove the last operator and replace it with the new one
            String updatedText = currentText.substring(0, currentText.length() - 1) + newOperator;
            display.setText(updatedText);
            display.setSelection(updatedText.length()); // Move cursor to end
        } else {
            // If the last character is not an operator, just add the new operator
            updateText(newOperator);
        }
    }

    private boolean isLastCharDecimal() {
        String currentText = display.getText().toString();
        if (currentText.isEmpty()) {
            return false;
        }
        char lastChar = currentText.charAt(currentText.length() - 1);
        return lastChar == '.';
    }

}