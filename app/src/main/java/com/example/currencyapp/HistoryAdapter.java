package com.example.currencyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<ConversionHistoryItem> historyList;
    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());


    public HistoryAdapter(List<ConversionHistoryItem> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        ConversionHistoryItem item = historyList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView conversionTextView;
        TextView timestampTextView;

        HistoryViewHolder(View itemView) {
            super(itemView);
            conversionTextView = itemView.findViewById(R.id.conversion_text);
            timestampTextView = itemView.findViewById(R.id.timestamp_text);
        }

        void bind(ConversionHistoryItem item) {
            String conversionText = String.format("%s %s to %s %s",
                    decimalFormat.format(item.getOriginalAmount()),
                    item.getFromCurrency(),
                    decimalFormat.format(item.getConvertedAmount()),
                    item.getToCurrency());
            conversionTextView.setText(conversionText);

            if (item.getTimestamp() != null) {
                timestampTextView.setText(dateFormat.format(item.getTimestamp()));
            }
        }
    }
}
