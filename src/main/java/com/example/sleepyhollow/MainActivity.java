package com.example.sleepyhollow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        queue = Volley.newRequestQueue(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usernameEditText.getText().toString().trim();
                String pass = passwordEditText.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "http://10.0.2.2:8080/sleepyhollow/login?user=" + user + "&pass=" + pass;

                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response != null) {
                                    response = response.trim();
                                    if (response.startsWith("Yes:") && response.contains(":")) {
                                        String[] parts = response.split(":");
                                        if (parts.length == 2) {
                                            String ssn = parts[1].trim();

                                            if (ssn.isEmpty()) {
                                                return;
                                            }
                                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                            intent.putExtra("ssn", ssn);
                                            startActivity(intent);
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login failed: " + response, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(request);
            }
        });
    }
}
