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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {

    Spinner spinnerAwards;
    TextView awardDetailsTextView;
    ArrayList<String> awardIds;
    ArrayAdapter<String> adapter;
    String ssn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        spinnerAwards = findViewById(R.id.spinnerAwards);
        awardDetailsTextView = findViewById(R.id.textViewAwardDetails);

        ssn = getIntent().getStringExtra("ssn");
        if (ssn == null || ssn.isEmpty()) {
            Toast.makeText(this, "No SSN received", Toast.LENGTH_SHORT).show();
            return;
        }

        awardIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, awardIds);
        spinnerAwards.setAdapter(adapter);

        loadAwardIds();

        spinnerAwards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String awardId = awardIds.get(position);
                loadAwardDetails(awardId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                awardDetailsTextView.setText("");
            }
        });
    }

    private void loadAwardIds() {
        String url = "http://10.0.2.2:8080/sleepyhollow/AwardIds.jsp?ssn=" + ssn;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    awardIds.clear();
                    String[] ids = response.split("#");
                    for (String id : ids) {
                        if (!id.trim().isEmpty()) {
                            awardIds.add(id.trim());
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error loading awards", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(request);
    }

    private void loadAwardDetails(String awardId) {
        String url = "http://10.0.2.2:8080/sleepyhollow/GrantedDetails.jsp?award_id=" + awardId + "&ssn=" + ssn;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    String[] parts = response.split(",");
                    if (parts.length >= 2) {
                        String date = parts[0].trim();
                        String center = parts[1].trim();
                        StringBuilder sb = new StringBuilder();
                        sb.append(String.format("%-15s %s\n", "Award Date", "Award Center"));
                        sb.append("-----------------------------------\n");
                        sb.append(String.format("%-15s %s\n", date, center));
                        sb.append("-----------------------------------\n");
                        awardDetailsTextView.setText(sb.toString());
                    } else {
                        awardDetailsTextView.setText("Invalid Data!");
                    }
                },
                error -> Toast.makeText(this, "Error loading award details", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(request);
    }
}