package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.customview.customView.InputNumberView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputNumberView inputNumberView=findViewById(R.id.number_view);
        inputNumberView.setOnNumberNumberChangeListener(new InputNumberView.OnNumberNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                Toast.makeText(MainActivity.this, "num is "+value, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
