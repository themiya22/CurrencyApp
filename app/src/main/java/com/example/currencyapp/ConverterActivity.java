package com.example.currencyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;

public class ConverterActivity extends AppCompatActivity {
    //normal UI elemnts
    private EditText amountEditText;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private TextView resultLabelTextView;
    private TextView resultTextView;
    private Button btnConvert;
    private Button btnClear;
    private Button btnHome;

    //API config
    private static final String API_KEY = "fca_live_EfykldLsC9ilp6eghX4GRJWkmlvHEmkoeu9e8f5X";
    private static final String API_URL = "https://api.freecurrencyapi.com/v1/latest?apikey";

    //network config (referred from AI: Gemini)
    private RequestQueue requestQueue;
    private DecimalFormat decimalFormat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        //initializing volley (referred from AI: Gemini)
        requestQueue = Volley.newRequestQueue(this);
        decimalFormat = new DecimalFormat("#.##");

        //mapping UI elemnts
        amountEditText = findViewById(R.id.TxtAmount);
        fromSpinner = findViewById(R.id.spinner_FROM);
        toSpinner = findViewById(R.id.spinner_To);
        resultLabelTextView = findViewById(R.id.txtResults);
        Button btnConvert = findViewById(R.id.btnConvert);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnHome = findViewById(R.id.btnHome);

        //results
        resultLabelTextView = findViewById(R.id.lblResults);
        resultTextView = findViewById(R.id.txtResults);

        resultLabelTextView.setVisibility(View.GONE); //to make the label disapear when the screen opens
        resultTextView.setText(getString(R.string.converter_initial_value)); // set a readable intial val

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });


    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //referenced from AI: Gemini
        startActivity(intent);
        finish(); //simply to close it
    }

    private void clearFields() {
        amountEditText.setText("");
        fromSpinner.setSelection(0);
        toSpinner.setSelection(0);
        Toast.makeText(this, "Fields Cleared", Toast.LENGTH_SHORT).show();
    }

    private void performConversion() {
        String amtStr = amountEditText.getText().toString();
        String fromCurrency = fromSpinner.getSelectedItem().toString();
        String toCurrency = toSpinner.getSelectedItem().toString();

        if(amtStr.isEmpty() || amtStr.equals("."))
        {
            amountEditText.setError("please enter a valid amount");
            return;
        }
        double amount = Double.parseDouble(amtStr);
        fetchExchangeRate(fromCurrency, toCurrency, amount);   //enter a try catch later
    }

    private void fetchExchangeRate(String fromCurrency, String toCurrency, double amount) {
    }

    private void displayConversionResult(double result, String currencyCode) {
        String formattedResult = decimalFormat.format(result) + " " + currencyCode;
        resultTextView.setText(formattedResult);
        resultLabelTextView.setVisibility(View.VISIBLE);
    }
}
