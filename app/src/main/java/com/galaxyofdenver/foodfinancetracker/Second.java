package com.galaxyofdenver.foodfinancetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static java.sql.Types.NULL;

public class Second extends AppCompatActivity {
    private Button createBudget;
    private Button resetButton;
    public Button submitBudget;
    double num = 0;
    double totalspentDouble = 0;
    double totalBudgetDouble;
    //SharedPreferences pricespentp = getSharedPreferences("com.galaxyofdenver.foodfinancetracker", Context.MODE_PRIVATE);
    //String priceSpentKey = "com.galaxyofdenver.foodfinancetracker.pricespent";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Calling onCreate");
        String budget = getPrefBudget();
        System.out.println("STRING BUD before getBudget " + budget);


        //System.out.println("GETTING PREF AT TOP OF ON CREATE" + t);
        setContentView(R.layout.activity_second);
        sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String spent = getPrefSpent();
        totalspentDouble = Double.parseDouble(spent);


        setPercentage(getPercent(totalBudgetDouble, totalspentDouble));


        setSpendingAdd(totalspentDouble);


        createBudget = (Button) findViewById(R.id.createBudget);
        createBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateBudget();
            }
        });


        resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences("budgetKey", "0");
                setPreferences("spent", "0");
                totalBudgetDouble = 0;
                totalspentDouble = 0;
                displaySpending(totalspentDouble);
                displayBudget();
            }
        });
        displayBudget();
    }


    public void displayBudget() {
        String budget = getPrefBudget();
        TextView tView = (TextView) findViewById(R.id.tBud);
        double dub = Double.parseDouble(budget);
        totalBudgetDouble = dub;
        tView.setText(NumberFormat.getCurrencyInstance().format(dub));
        setPercentage(getPercent(totalBudgetDouble,totalspentDouble));

    }
    public void displaySpending(double spending) {
        //access textview totalSpent
        TextView tBud = (TextView) findViewById(R.id.totalSpent);
        //set totalSpent textview number to new total
        tBud.setText(NumberFormat.getCurrencyInstance().format(spending));
        //setting percentage of budget spent
        setPercentage(getPercent(totalBudgetDouble, totalspentDouble));
    }

   // public String getCurrentPriceString(String string) {
        //return string;
    //}
    //public String getSpendString(String stringKey, String stringValue) {
      //  return pricespentp.getString(stringKey, stringValue);
    //}
/*    public void Budget() {
        //Intent intent = new Intent(this, Second.class);
        EditText priceBudget = (EditText) findViewById(R.id.edit);
        //store EditText input into string called pBud
        String pBud = priceBudget.getText().toString();
        double priceBudgetNum = Double.parseDouble(pBud);
        setPreferences("budgetKey", pBud);

        //create intent

        //intent.putExtra("budget_data", priceBudgetNum);
        totalBudgetDouble = priceBudgetNum;
    }*/
    public void setPreferences(String keyString, String valueString) {
        //sp.edit().putString(keyString, valueString).apply();
        sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keyString, valueString);
        editor.apply();
    }

    public String getPrefSpent() {
        sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String spent = sp.getString("spent","0");
        return spent;
    }

    public String getPrefBudget() {
        sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String budget = sp.getString("budgetKey","0");
        return budget;
    }



    public void onSubmit(View v) {
            //if (totalBudgetDouble == 0) {
                //Alert();
            EditText priceSpent = (EditText) findViewById(R.id.spend);
            if (isEmpty(priceSpent)) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            } else {
                //get ID of EditText field

                //store EditText input into string called pBud
                String pBud = priceSpent.getText().toString();
                if (isEmpty(priceSpent)) {
                    Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
                } else {
                    double price = Double.parseDouble(pBud);
                    //store double value from string into a double

                    setSpendingAdd(price);
                    priceSpent.getText().clear();
                }

            }
    }

    public void onSubtract(View v) {
        if (totalspentDouble == 0) {
            Toast.makeText(this, "You can not have negative spending", Toast.LENGTH_SHORT).show();
        } else if (totalBudgetDouble - totalspentDouble < 0) {
            Toast.makeText(this, "You can not have negative spending", Toast.LENGTH_SHORT).show();
        } else {



            //get ID of EditText field
            EditText priceSpent = (EditText) findViewById(R.id.spend);
            //store EditText input into string called pBud
            String pBud = priceSpent.getText().toString();

            if (isEmpty(priceSpent)) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            } else {
                double price = Double.parseDouble(pBud);
                //store double value from string into a double

                setSpendingSubtract(price);
                priceSpent.getText().clear();
            }

        }
    }


    public void setBudget() {
            ////create intent
          //  Intent intent = this.getIntent();
            //store value of retrieved intent
          //  double num = intent.getDoubleExtra("budget_data", 0);

            //totalBudgetDouble = num;

           // String string = Double.toString(num);
            //setPreferences("budgetKey", string);

            //log: check string
            //System.out.println("TESTING STRING: " + string);
            TextView tBud = (TextView) findViewById(R.id.tBud);
            tBud.setText(NumberFormat.getCurrencyInstance().format(totalBudgetDouble));
        }




    // method to add more money to money spent total
    // @param record spending input
    public void setSpendingAdd(double price) {
        System.out.println("BUDGET TOTAL = " + totalBudgetDouble);
            //saving spent total
            num += price;
            totalspentDouble = num;
            Intent intent = new Intent(this, CreateBudgetActivity.class);
            //save variable priceBudgetNum to intent, to be used in Second.class
            intent.putExtra("total_spent", totalspentDouble);

            //log : total spent
            System.out.println("TOTAL SPENT = " + totalspentDouble);

            //save double to string to be stored to SharedPref
            String pBud = Double.toString(totalspentDouble);

            //log: check string
            System.out.println("TESTING STRING: " + pBud);
            //log:saving preference spent
            System.out.println("SETTING PREFERENCE: spent");
            setPreferences("spent", pBud);
            //log:testing that preference was saved
            String loaded = "PREFERENCE LOADED: " + getPrefSpent();
            System.out.println(loaded);

            displaySpending(num);
    }

    // method to subtract more money from money spent total
    // @param record spending input
    public void setSpendingSubtract(double price) {
        System.out.println("BUDGET TOTAL = " + totalBudgetDouble);

        num -= price;
        totalspentDouble = num;
        Intent intent = new Intent(this, CreateBudgetActivity.class);
        //save variable priceBudgetNum to intent, to be used in Second.class
        intent.putExtra("total_spent", totalspentDouble);

        //log : total spent
        System.out.println("TOTAL SPENT = " + totalspentDouble);

        //save double to string to be stored to SharedPref
        String pBud = Double.toString(totalspentDouble);

        //log: check string
        System.out.println("TESTING STRING: " + pBud);
        //log:saving preference spent
        System.out.println("SETTING PREFERENCE: spent");
        setPreferences("spent", pBud);
        //log:testing that preference was saved
        String loaded = "PREFERENCE LOADED: " + getPrefSpent();
        System.out.println(loaded);

        displaySpending(num);
    }

    public double getPercent(double budget, double spending) {
        System.out.println("GETTING PERCENT:");
        double per = 0;
        if (budget == 0) {
        }
        if (spending == 0) {
        }
        if (spending/budget == 1) {
            per = 100;
        }
        if (budget > spending) {
            per = (spending / budget) * 100;
            System.out.println(per);
        } else {
            per = (spending / budget) * 100;
        }

        System.out.println(" " + per + "%");
        return per;
    }

    public void setPercentage(double per) {
        if (totalBudgetDouble == 0 && totalspentDouble == 0) {
            per = 0;
        }
        System.out.println("SETTING PERCENT:");
        TextView tPer = (TextView) findViewById(R.id.percent);
        //store num to text view
        DecimalFormat df = new DecimalFormat("#.##");
        tPer.setText(""+df.format(per)+"%");
        System.out.println(""+df.format(per)+"%");
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    // get pref , need to DoubleParse
    //opens activity CreateBudgetActivity
    public void openCreateBudget() {
        Intent cBudget = new Intent(this, CreateBudgetActivity.class);
        startActivity(cBudget);
    }


}

