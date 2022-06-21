package com.prasoon.wss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    TextView login_signup;
    EditText username, password, name;
    Button signup;
    AlertDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.loading, null);
        loadingDialog = new AlertDialog.Builder(RegisterActivity.this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        login_signup = findViewById(R.id.login_signup);
        name = findViewById(R.id.register_name);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(v->{
            if(name.getText().toString().trim().isEmpty() || username.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            postData(name.getText().toString(), username.getText().toString().toLowerCase().trim(), password.getText().toString().toLowerCase().trim());
        });
        login_signup.setOnClickListener(v->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    public void postData(String name, String username, String password){
        loadingDialog.show();
        JSONObject body = new JSONObject();
        String URL = getString(R.string.BASE_URL)+"/user/register";
        try {
            body.put("name", name);
            body.put("username", username);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, body, response -> {
            try {
                boolean success = response.getBoolean("success");
                if(success){
                    Toast.makeText(RegisterActivity.this, "Account created Successfully, You can login now.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.hide();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(RegisterActivity.this, "Internet connection error. Please try again.", Toast.LENGTH_SHORT).show();
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}