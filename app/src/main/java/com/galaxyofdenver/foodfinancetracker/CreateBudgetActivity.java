package com.galaxyofdenver.foodfinancetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class CreateBudgetActivity extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_budget);
        Intent intent = new Intent(this, Second.class);
        intent.putExtra("checkSubmit", false);
    }

    public void onSubmit(View v) {
        EditText priceBudget = (EditText) findViewById(R.id.edit);
        String pBud = priceBudget.getText().toString();
        sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("budgetKey", pBud);
        editor.apply();
        String string = sp.getString("budgetKey", "0");
        System.out.println("GETTING BUDGETKEY IN ONSUBMIT " +  string);
        Intent intent = new Intent(this, Second.class);
        startActivity(intent);
    }


/*    public void onSubmit(View v) {
        //get ID of EditText field
        Intent intent = new Intent(this, Second.class);
        EditText priceBudget = (EditText) findViewById(R.id.edit);
        //store EditText input into string called pBud
        String pBud = priceBudget.getText().toString();
        double priceBudgetNum = Double.parseDouble(pBud);

        //create intent

        intent.putExtra("budget_data", priceBudgetNum);

        startActivity(intent);
    }*/

}
