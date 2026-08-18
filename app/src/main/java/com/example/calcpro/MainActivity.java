package com.example.calcpro;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText tv = findViewById(R.id.tvDisplay);
        int[] ids = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for(int id : ids) {
            findViewById(id).setOnClickListener(v -> tv.append(((Button)v).getText()));
        }
        findViewById(R.id.btnClear).setOnClickListener(v -> tv.setText(""));
    }
}
