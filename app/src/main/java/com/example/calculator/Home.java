package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Spinner option = (Spinner) findViewById(R.id.options);
        TextView myTextView = findViewById(R.id.txtOptionValue);
        String[] items = {"John Doe", "123456789", "Learning the basics of Android Studio has been an exciting yet challenging journey for me as a student. At first, the complexity of the interface and tools felt overwhelming, but with time, I started getting more comfortable navigating the environment. Understanding how to create layouts, use XML for UI design, and write Java code for functionality helped me realize the potential of building real-world apps. I’ve gained a sense of accomplishment with each step, from running my first emulator to seeing the app come to life. Although there’s still a lot to learn, I’m eager to continue exploring and improving."};

        option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();

                myTextView.setText(items[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnCalculator = (Button) findViewById(R.id.btnCalculator);
        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, MainActivity.class));
            }
        });
    }
}