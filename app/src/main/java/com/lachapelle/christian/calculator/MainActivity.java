package com.lachapelle.christian.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure Scrollbars
        TextView history = (TextView) findViewById(R.id.txtHistory);
        history.setMovementMethod(new ScrollingMovementMethod());

        // Setup the buttons and OnClick listeners
        Button btnOne = (Button) findViewById(R.id.btnOne);
        btnOne.setOnClickListener(view -> updateScreen(btnOne, false));

        Button btnTwo = (Button) findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(view -> updateScreen(btnTwo, false));

        Button btnThree = (Button) findViewById(R.id.btnThree);
        btnThree.setOnClickListener(view -> updateScreen(btnThree, false));

        Button btnFour = (Button) findViewById(R.id.btnFour);
        btnFour.setOnClickListener(view -> updateScreen(btnFour, false));

        Button btnFive = (Button) findViewById(R.id.btnFive);
        btnFive.setOnClickListener(view -> updateScreen(btnFive, false));

        Button btnSix = (Button) findViewById(R.id.btnSix);
        btnSix.setOnClickListener(view -> updateScreen(btnSix, false));

        Button btnSeven = (Button) findViewById(R.id.btnSeven);
        btnSeven.setOnClickListener(view -> updateScreen(btnSeven, false));

        Button btnEight = (Button) findViewById(R.id.btnEight);
        btnEight.setOnClickListener(view -> updateScreen(btnEight, false));

        Button btnNine = (Button) findViewById(R.id.btnNine);
        btnNine.setOnClickListener(view -> updateScreen(btnNine, false));

        Button btnZero = (Button) findViewById(R.id.btnZero);
        btnZero.setOnClickListener(view -> updateScreen(btnZero, false));

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> updateScreen(btnAdd, false));

        Button btnSubtract = (Button) findViewById(R.id.btnSubtract);
        btnSubtract.setOnClickListener(view -> updateScreen(btnSubtract, false));

        Button btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnMultiply.setOnClickListener(view -> updateScreen(btnMultiply, false));

        Button btnDivide = (Button) findViewById(R.id.btnDivide);
        btnDivide.setOnClickListener(view -> updateScreen(btnDivide, false));

        Button btnEqual = (Button) findViewById(R.id.btnEqual);
        btnEqual.setOnClickListener(view -> updateScreen(btnEqual, false));

        Button btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(view -> updateScreen(btnClear, false));
        btnClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                updateScreen(btnClear, true);
                return false;
            }
        });

        Button btnRBracket = (Button) findViewById(R.id.btnRBracket);
        btnRBracket.setOnClickListener(view -> updateScreen(btnRBracket, false));

        Button btnLBracket = (Button) findViewById(R.id.btnLBracket);
        btnLBracket.setOnClickListener(view -> updateScreen(btnLBracket, false));

        Button btnDecimal = (Button) findViewById(R.id.btnDecimal);
        btnDecimal.setOnClickListener(view -> updateScreen(btnDecimal, false));
    }

    private void updateScreen(View view, boolean lngClick){
        NoVKEditText editText;
        editText = (NoVKEditText) findViewById(R.id.txtValues);
        TextView historyText;
        historyText = (TextView) findViewById(R.id.txtHistory);

        if (view.getId() == R.id.btnOne){
            editText.append("1");
        }
        else if (view.getId() == R.id.btnTwo){
            editText.append("2");
        }
        else if (view.getId() == R.id.btnThree){
            editText.append("3");
        }
        else if (view.getId() == R.id.btnFour){
            editText.append("4");
        }
        else if (view.getId() == R.id.btnFive){
            editText.append("5");
        }
        else if (view.getId() == R.id.btnSix){
            editText.append("6");
        }
        else if (view.getId() == R.id.btnSeven){
            editText.append("7");
        }
        else if (view.getId() == R.id.btnEight){
            editText.append("8");
        }
        else if (view.getId() == R.id.btnNine){
            editText.append("9");
        }
        else if (view.getId() == R.id.btnZero){
            editText.append("0");
        }
        else if (view.getId() == R.id.btnAdd){
            editText.append(" + ");
        }
        else if (view.getId() == R.id.btnSubtract){
            editText.append(" - ");
        }
        else if (view.getId() == R.id.btnMultiply){
            editText.append(" * ");
        }
        else if (view.getId() == R.id.btnDivide){
            editText.append(" / ");
        }
        else if (view.getId() == R.id.btnRBracket){
            editText.append(")");
        }
        else if (view.getId() == R.id.btnLBracket){
            editText.append("(");
        }
        else if (view.getId() == R.id.btnClear){
            if (lngClick){
                historyText.setText("");
            }
            editText.setText("");
        }
        else if (view.getId() == R.id.btnEqual){
            if (!historyText.getText().toString().equals("")){
                historyText.append("\n");
            }
            String result = calculate(editText.getText().toString());

            if (result.equals("ERROR")){
                historyText.append(result);
            }
            else{
                historyText.append(editText.getText().toString() + " = " + result);
                editText.setText(result);
            }
        }
        else if (view.getId() == R.id.btnDecimal){
            // Only allow for one decimal point in a value.
            // If starting a value with . add a 0 in front of it.
            String[] parts = editText.getText().toString().split(" ");
            String lastExpression = parts[parts.length - 1];

            if (lastExpression.contains("+") ||
                    lastExpression.contains("-") ||
                    lastExpression.contains("*") ||
                    lastExpression.contains("/")){
                editText.append("0.");
            }
            else if (!lastExpression.contains(".")){
                editText.append(".");
            }
        }
    }

    private String calculate(String expression){
        String engineName = "rhino";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName(engineName);

        if (engine == null){
            throw new UnsupportedOperationException("Engine not found: " + engineName);
        }

        try{
            Object results = engine.eval(expression);
            return results.toString();
        }
        catch (ScriptException e) {
            return "ERROR";
        }
    }
}