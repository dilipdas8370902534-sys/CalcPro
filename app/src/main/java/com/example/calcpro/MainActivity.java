package com.example.calcpro;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText tvDisplay;
    private TextView tvHistory;
    private String firstNum = "";
    private String operator = "";
    private boolean isNewOp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        tvHistory = findViewById(R.id.tvHistory);

        // Number Buttons
        int[] numericButtons = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        View.OnClickListener numberListener = v -> {
            Button b = (Button) v;
            if (isNewOp) tvDisplay.setText("");
            isNewOp = false;
            tvDisplay.append(b.getText().toString());
        };
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(numberListener);
        }

        // Basic Operators
        int[] opButtons = {R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv};
        View.OnClickListener opListener = v -> {
            Button b = (Button) v;
            firstNum = tvDisplay.getText().toString();
            operator = b.getText().toString();
            isNewOp = true;
        };
        for (int id : opButtons) {
            findViewById(id).setOnClickListener(opListener);
        }

        // Equal Button
        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            String secondNum = tvDisplay.getText().toString();
            if (!firstNum.isEmpty() && !secondNum.isEmpty()) {
                double res = calculate(Double.parseDouble(firstNum), Double.parseDouble(secondNum), operator);
                String resultStr = String.valueOf(res);
                tvDisplay.setText(resultStr);
                tvHistory.setText("History: " + firstNum + " " + operator + " " + secondNum + " = " + resultStr);
                isNewOp = true;
            }
        });

        // Scientific Functions
        findViewById(R.id.btnSin).setOnClickListener(v -> calcScientific("sin"));
        findViewById(R.id.btnCos).setOnClickListener(v -> calcScientific("cos"));
        findViewById(R.id.btnTan).setOnClickListener(v -> calcScientific("tan"));
        findViewById(R.id.btnSqrt).setOnClickListener(v -> calcScientific("sqrt"));
        findViewById(R.id.btnLog).setOnClickListener(v -> calcScientific("log"));
        findViewById(R.id.btnPercent).setOnClickListener(v -> calcScientific("percent"));

        // Clear Button
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            tvDisplay.setText("");
            firstNum = "";
            operator = "";
            isNewOp = true;
        });

        // Copy & Paste
        findViewById(R.id.btnCopy).setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("CalcResult", tvDisplay.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "কপি করা হয়েছে!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnPaste).setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard.hasPrimaryClip()) {
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                tvDisplay.setText(item.getText().toString());
            }
        });
    }

    private double calculate(double n1, double n2, String op) {
        switch (op) {
            case "+": return n1 + n2;
            case "-": return n1 - n2;
            case "*": return n1 * n2;
            case "/": return n2 != 0 ? n1 / n2 : 0;
            default: return 0;
        }
    }

    private void calcScientific(String type) {
        String val = tvDisplay.getText().toString();
        if (!val.isEmpty()) {
            double n = Double.parseDouble(val);
            double res = 0;
            switch (type) {
                case "sin": res = Math.sin(Math.toRadians(n)); break;
                case "cos": res = Math.cos(Math.toRadians(n)); break;
                case "tan": res = Math.tan(Math.toRadians(n)); break;
                case "sqrt": res = Math.sqrt(n); break;
                case "log": res = Math.log10(n); break;
                case "percent": res = n / 100; break;
            }
            tvDisplay.setText(String.valueOf(res));
            tvHistory.setText("History: " + type + "(" + val + ") = " + res);
            isNewOp = true;
        }
    }
}
