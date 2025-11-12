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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DecimalFormat;

public class ConverterActivity extends AppCompatActivity {
    //normal UI elemnts
    private EditText amountEditText;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private TextView resultLabelTextView;
    private TextView resultTextView;

    //API config (referred from AI tool:Gemini)
    private static final String API_KEY = "fca_live_EfykldLsC9ilp6eghX4GRJWkmlvHEmkoeu9e8f5X";
    private static final String API_URL = "https://api.freecurrencyapi.com/v1/latest";

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
        resultLabelTextView = findViewById(R.id.lblResults);
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
        resultTextView.setText(getString(R.string.converter_initial_value));
    }

    private void performConversion() {
        try {
            String amtStr = amountEditText.getText().toString();
            String fromCurrency = fromSpinner.getSelectedItem().toString();
            String toCurrency = toSpinner.getSelectedItem().toString();

            if (amtStr.isEmpty() || amtStr.equals(".")) {
                amountEditText.setError("please enter a valid amount");
                return;
            }
            double amount = Double.parseDouble(amtStr);
            fetchExchangeRate(fromCurrency, toCurrency, amount);
        } catch(Exception e)
        {
            Toast.makeText(this, "ERROR! Please try again later.", Toast.LENGTH_LONG).show();
            e.printStackTrace(); //to print to the terminal
        }
    }

    //reffered from worksheets and AI tool: Gemini
    private void fetchExchangeRate(String fromCurrency, String toCurrency, double amount) {
        String url = API_URL +
                "?apikey=" + API_KEY +
                "&base_currency=" + fromCurrency +
                "&currencies=" + toCurrency;

        //referred from Gemini ans worksheet 9
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //refrred from Gemini ans worksheet 9
                    JSONObject data = response.getJSONObject("data");

                    double rate = data.getDouble(toCurrency);
                    double convertedAmount = amount * rate;

                    displayConversionResult(convertedAmount, toCurrency);
                    //worksheet 6
                    Toast.makeText(ConverterActivity.this, "Conversion successful", Toast.LENGTH_SHORT).show();

                } catch (org.json.JSONException e) {
                    Toast.makeText(ConverterActivity.this, "API Error: Invalid response or currency not found.", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConverterActivity.this, "ERROR! Please try again later.", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(request);
    }

    private void displayConversionResult(double result, String currencyCode) {
        String formattedResult = decimalFormat.format(result) + " " + currencyCode;
        resultTextView.setText(formattedResult);
        resultLabelTextView.setVisibility(View.VISIBLE);
    }
}
