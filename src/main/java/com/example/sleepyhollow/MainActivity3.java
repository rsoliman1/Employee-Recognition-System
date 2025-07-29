package com.example.sleepyhollow;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class MainActivity3 extends AppCompatActivity {

    TextView textViewTransactions;
    RequestQueue queue;
    String ssn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textViewTransactions = findViewById(R.id.textViewTransactions);
        queue = Volley.newRequestQueue(this);
        ssn = getIntent().getStringExtra("ssn");

        if (ssn != null && !ssn.isEmpty()) {
            loadTransactions();
        }
    }
    void loadTransactions() {
        try {
            String url = "http://10.0.2.2:8080/sleepyhollow/Transactions.jsp?ssn=" + URLEncoder.encode(ssn, "UTF-8");

            StringRequest request = new StringRequest(Request.Method.GET, url,
                    response -> {
                        String[] transactions = response.trim().split("#");
                        StringBuilder result = new StringBuilder();
                        result.append(String.format("%-8s %17s %10s\n", "Txn ID", "date", "amount"));
                        result.append("--------------------------------------\n");

                        for (String item : transactions) {
                            String[] parts = item.split(",");
                            if (parts.length == 3) {
                                String txnId = parts[0].trim();
                                String rawDate = parts[1].trim();
                                String formattedDate = rawDate;

                                try {
                                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
                                    formattedDate = formatter.format(parser.parse(rawDate));
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity3.this, "Error!", Toast.LENGTH_SHORT).show();
                                }

                                String amount = parts[2].trim();
                                result.append(String.format("%-8s %17s %10s\n", txnId, formattedDate, amount));
                            }
                        }
                        result.append("--------------------------------------\n");
                        textViewTransactions.setText(result.toString());
                    },
                    error -> textViewTransactions.setText("Error: cannot load Transactions"));

            queue.add(request);

        } catch (Exception e) {
            Toast.makeText(MainActivity3.this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }
}
