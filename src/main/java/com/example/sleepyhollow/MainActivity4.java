package com.example.sleepyhollow;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    Spinner spinnerTransactions;
    TextView textViewTransactionDetails;
    RequestQueue queue;
    String ssn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        spinnerTransactions = findViewById(R.id.spinnerTransactions);
        textViewTransactionDetails = findViewById(R.id.textViewTransactionDetails);
        queue = Volley.newRequestQueue(this);

        ssn = getIntent().getStringExtra("ssn");
        if (ssn == null || ssn.isEmpty()) {
            Toast.makeText(this, "No SSN provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadTransactionIds();
    }

    void loadTransactionIds() {
        try {
            String url = "http://10.0.2.2:8080/sleepyhollow/Transactions.jsp?ssn=" + URLEncoder.encode(ssn, "UTF-8");
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    response -> {
                        String[] items = response.trim().split("#");
                        ArrayList<String> transactionIds = new ArrayList<>();
                        for (String item : items) {
                            if (!item.trim().isEmpty()) {
                                String[] parts = item.split(",");
                                if (parts.length >= 1) {
                                    transactionIds.add(parts[0].trim());
                                }
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, transactionIds);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerTransactions.setAdapter(adapter);

                        spinnerTransactions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String txnid = transactionIds.get(position);
                                loadTransactionDetails(txnid);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                textViewTransactionDetails.setText("");
                            }
                        });
                    },
                    error -> Toast.makeText(this, "Error loading transactions", Toast.LENGTH_SHORT).show()
            );
            queue.add(request);
        } catch (Exception e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    void loadTransactionDetails(String txnid) {
        try {
            String url = "http://10.0.2.2:8080/sleepyhollow/TransactionDetails.jsp?txnid=" + URLEncoder.encode(txnid, "UTF-8");
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    response -> {
                        String[] lines = response.trim().split("#");
                        StringBuilder sb = new StringBuilder();

                        if (lines.length > 0) {
                            String[] header = lines[0].split(",");
                            if (header.length >= 2) {
                                String rawDate = header[0].trim();
                                String formattedDate = rawDate;
                                try {
                                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
                                    formattedDate = formatter.format(parser.parse(rawDate));
                                } catch (Exception e) {
                                }

                                sb.append("Date: ").append(formattedDate).append("\n");
                                sb.append("Amount: $").append(header[1].trim()).append("\n\n");
                                sb.append(String.format("%-15s %-8s %s\n", "Prod Name", "Price", "Qty"));
                                sb.append("--------------------------------------\n");
                            }

                            for (int i = 1; i < lines.length; i++) {
                                String[] parts = lines[i].split(",");
                                if (parts.length == 3) {
                                    sb.append(String.format("%-15s %-8s %s\n",
                                            parts[0].trim(), parts[1].trim(), parts[2].trim()));
                                }
                            }
                            sb.append("--------------------------------------");
                        }

                        textViewTransactionDetails.setText(sb.toString());
                    },
                    error -> Toast.makeText(this, "Error loading transaction details", Toast.LENGTH_SHORT).show()
            );
            queue.add(request);
        } catch (Exception e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }
}