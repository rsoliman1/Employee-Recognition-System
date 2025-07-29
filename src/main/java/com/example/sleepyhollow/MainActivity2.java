package com.example.sleepyhollow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.net.URLEncoder;

public class MainActivity2 extends AppCompatActivity {

    TextView nameTextView, salesTextView;
    ImageView photoImageView;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nameTextView = findViewById(R.id.textViewName);
        salesTextView = findViewById(R.id.textViewSales);
        photoImageView = findViewById(R.id.imageViewPhoto);
        queue = Volley.newRequestQueue(this);

        String ssn = getIntent().getStringExtra("ssn");
        if (ssn == null || ssn.isEmpty()) {
            Toast.makeText(this, "No SSN provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        try {
            String url = "http://10.0.2.2:8080/sleepyhollow/Info.jsp?ssn=" + URLEncoder.encode(ssn, "UTF-8");
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    response -> {
                        String[] rows = response.trim().split("#");
                        if (rows.length >= 1) {
                            String[] parts = rows[0].split(",");
                            if (parts.length == 2) {
                                nameTextView.setText(parts[0].trim());
                                salesTextView.setText("$" + parts[1].trim());
                                String imageUrl = "http://10.0.2.2:8080/sleepyhollow/images/" + ssn + ".jpeg";
                                Glide.with(this).load(imageUrl).into(photoImageView);
                            }
                        }
                    },
                    error -> Toast.makeText(this, "Error: " + error.toString(), Toast.LENGTH_LONG).show());

            queue.add(request);

        } catch (Exception e) {
            Toast.makeText(this, "Encoding error", Toast.LENGTH_SHORT).show();
        }

        Button buttonTransactions = findViewById(R.id.buttonTransactions);
        Button buttonTransactionDetails = findViewById(R.id.buttonTransactionDetails);
        Button buttonAwardDetails = findViewById(R.id.buttonAwardDetails);
        Button buttonTransfer = findViewById(R.id.buttonTransfer);
        Button buttonExit = findViewById(R.id.buttonExit);

        buttonTransactions.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            intent.putExtra("ssn", ssn);
            startActivity(intent);
        });

        buttonTransactionDetails.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
            intent.putExtra("ssn", ssn);
            startActivity(intent);
        });

        buttonAwardDetails.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
            intent.putExtra("ssn", ssn);
            startActivity(intent);
        });

        buttonTransfer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity6.class);
            intent.putExtra("ssn", ssn);
            startActivity(intent);
        });
        buttonExit.setOnClickListener(v -> {
            Toast.makeText(MainActivity2.this, "See you later!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });
    }
}