package com.example.currencyapp;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class ConversionHistoryItem {
    private String fromCurrency;
    private String toCurrency;
    private double originalAmount;
    private double convertedAmount;
    private Date timestamp;

    // Required empty public constructor for Firestore deserialization
    public ConversionHistoryItem() {
    }

    public ConversionHistoryItem(String fromCurrency, String toCurrency, double originalAmount, double convertedAmount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
