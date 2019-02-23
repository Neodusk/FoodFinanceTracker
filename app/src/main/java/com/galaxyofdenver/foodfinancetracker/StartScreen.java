package com.galaxyofdenver.foodfinancetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;


public class StartScreen extends AppCompatActivity {
    //SharedPreferences sharedpreferences;
    SharedPreferences sp;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        TextView tView = (TextView) findViewById(R.id.start);
        sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String string = sp.getString("spent", "0");
        double dub = Double.parseDouble(string);
        tView.setText(NumberFormat.getCurrencyInstance().format(dub));

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }
    public void openActivity2() {
        Intent intent = new Intent(this, Second.class);
        startActivity(intent);
    }
}
