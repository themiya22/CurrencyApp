package com.example.currencyapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "HistoryActivity";
    private FirebaseFirestore db;
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private List<ConversionHistoryItem> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = FirebaseFirestore.getInstance();
        historyRecyclerView = findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyList);
        historyRecyclerView.setAdapter(historyAdapter);

        Button btnHome = findViewById(R.id.btn_home);
        Button btnClearHistory = findViewById(R.id.btn_clear_history);

        btnHome.setOnClickListener(v -> openHomeActivity());
        btnClearHistory.setOnClickListener(v -> clearHistory());

        fetchConversionHistory();
    }

    // Fetch conversion history (referred from AI: Gemini)
    private void fetchConversionHistory() {
        db.collection("conversionHistory")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        historyList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ConversionHistoryItem item = document.toObject(ConversionHistoryItem.class);
                            historyList.add(item);
                        }
                        historyAdapter.notifyDataSetChanged();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    private void clearHistory() {
        db.collection("conversionHistory").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                WriteBatch batch = db.batch();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    batch.delete(document.getReference());
                }
                batch.commit().addOnCompleteListener(batchTask -> {
                    if (batchTask.isSuccessful()) {
                        historyList.clear();
                        historyAdapter.notifyDataSetChanged();
                        Toast.makeText(HistoryActivity.this, "History Cleared", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.w(TAG, "Error clearing history", batchTask.getException());
                        Toast.makeText(HistoryActivity.this, "Failed to clear history", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Log.w(TAG, "Error getting documents to delete", task.getException());
            }
        });
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
