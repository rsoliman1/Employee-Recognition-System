package com.example.sleepyhollow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity6 extends AppCompatActivity {

    EditText editTextDestSSN, editTextAmount;
    Button buttonTransfer;
    String sourceSSN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        editTextDestSSN = findViewById(R.id.editTextDestSSN);
        editTextAmount = findViewById(R.id.editTextAmount);
        buttonTransfer = findViewById(R.id.buttonTransfer);

        sourceSSN = getIntent().getStringExtra("ssn");
        if (sourceSSN == null || sourceSSN.isEmpty()) {
            Toast.makeText(this, "No source SSN received", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destSSN = editTextDestSSN.getText().toString();
                String amount = editTextAmount.getText().toString();

                if (destSSN.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(MainActivity6.this, "Enter destination SSN and amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "http://10.0.2.2:8080/sleepyhollow/Transfer.jsp?ssn1=" + sourceSSN + "&ssn2=" + destSSN + "&amount=" + amount;

                StringRequest request = new StringRequest(Request.Method.GET, url,
                        response -> Toast.makeText(MainActivity6.this, response, Toast.LENGTH_LONG).show(),
                        error -> Toast.makeText(MainActivity6.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show());

                Volley.newRequestQueue(MainActivity6.this).add(request);
            }
        });
    }
}