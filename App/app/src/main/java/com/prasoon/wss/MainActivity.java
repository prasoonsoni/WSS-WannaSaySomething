package com.prasoon.wss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView name, url;
    AlertDialog loadingDialog;
    ImageView copy;
    Button messages, share, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.loading, null);
        loadingDialog = new AlertDialog.Builder(MainActivity.this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        name = findViewById(R.id.name);
        url = findViewById(R.id.url);
        copy = findViewById(R.id.copy);
        messages = findViewById(R.id.messages);
        share = findViewById(R.id.share);
        logout = findViewById(R.id.logout);

        messages.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, MessagesActivity.class));
        });

        share.setOnClickListener(v->{
            View view = LayoutInflater.from(this).inflate(R.layout.share_profile, null);
            TextView link = view.findViewById(R.id.link);
            link.setText(url.getText().toString());
            Bitmap image = getBitmapFromView(view);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), image, "Profile", null);
            Uri uri = Uri.parse(path);
            Intent feedIntent = new Intent(Intent.ACTION_SEND);
            feedIntent.setType("image/*");
            feedIntent.putExtra(Intent.EXTRA_STREAM, uri);
            feedIntent.putExtra(Intent.EXTRA_TEXT, "You can share anonymous messages to me at " + url.getText().toString() + "\n\nVisit "+getString(R.string.PROFILE_BASE_URL)+" to get anonymous messages!!");
            startActivity(Intent.createChooser(feedIntent, "Sharing Profile"));
        });

        logout.setOnClickListener(v->{
            SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", "");
            editor.apply();
            Toast.makeText(this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        copy.setOnClickListener(v->{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(name.getText().toString(), url.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Your Profile URL Copied.", Toast.LENGTH_SHORT).show();
        });

        url.setOnClickListener(v->{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url.getText().toString())));
        });
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        getData(token);
    }

    public void getData(String token){
        loadingDialog.show();
        String URL = getString(R.string.BASE_URL)+"/user/token/getuser";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean success = response.getBoolean("success");
                    if (success) {
                        JSONObject data = response.getJSONObject("data");
                        name.setText(data.getString("name")+"'s Profile");
                        url.setText(Html.fromHtml("<u>"+getString(R.string.PROFILE_BASE_URL)+data.getString("username")+"</u>"));
                        loadingDialog.hide();
                    } else {
                        Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        loadingDialog.hide();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialog.hide();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Internet connection error. Please try again.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                loadingDialog.hide();
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

    public Bitmap getBitmapFromView(View v){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        v.draw(c);

        return returnedBitmap;
    }

}