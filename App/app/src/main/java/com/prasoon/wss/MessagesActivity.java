
package com.prasoon.wss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prasoon.wss.adapters.MessageAdapter;
import com.prasoon.wss.models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesActivity extends AppCompatActivity {

    AlertDialog loadingDialog;
    List<Message> messages;
    Button go_back;
    RecyclerView messagesRecyclerView;
    MessageAdapter messageAdapter;
    TextView noMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messages = new ArrayList<>();
        go_back = findViewById(R.id.go_back);
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        noMessages = findViewById(R.id.noMessages);

        go_back.setOnClickListener(v->{
            startActivity(new Intent(MessagesActivity.this, MainActivity.class));
        });
        View dialogView = LayoutInflater.from(this).inflate(R.layout.loading, null);
        loadingDialog = new AlertDialog.Builder(MessagesActivity.this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        getData(token);
        messageAdapter = new MessageAdapter(messages, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messagesRecyclerView.setLayoutManager(layoutManager);
        messagesRecyclerView.setAdapter(messageAdapter);
    }

    public void getData(String token){
        loadingDialog.show();
        String URL = getString(R.string.BASE_URL)+"/message/all";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean success = response.getBoolean("success");
                    if (success) {
                        JSONArray data = response.getJSONArray("messages");
                        for(int i=0;i<data.length();i++){
                            JSONObject jsonObject = data.getJSONObject(i);
                            Log.i("message_object", jsonObject.toString());
                            Message message = new Message(jsonObject.getString("message"), jsonObject.getString("created_at"));
                            messages.add(message);
                        }
                        loadingDialog.dismiss();
                        messageAdapter.notifyDataSetChanged();
                        if(messageAdapter.getItemCount()==0){
                            noMessages.setVisibility(View.VISIBLE);
                        } else {
                            noMessages.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(MessagesActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MessagesActivity.this, "Internet connection error. Please try again.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loadingDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("auth-token", token);
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}