package com.example.cafeinadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Test extends AppCompatActivity implements View.OnClickListener {

    EditText edit1, edit2;
    Button btn;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAKpxS_tM:APA91bEDlv8Xnr-C4soEG96WDaUquB_b8qrKC1sZME_tBXU6jTsvtxdH701lcbyY7i1PUUrO1aBlS_pdNOjCDaKt_XRu5TPtMkw0nxPC6uh4Jg7uImLd3HNyQiTVNIA82_sBnDm1BvPD";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                NOTIFICATION_TITLE = edit1.getText().toString();
                NOTIFICATION_MESSAGE = edit2.getText().toString();

                JSONObject notification = new JSONObject();
                JSONObject notificationBody = new JSONObject();

                try {
                    notificationBody.put("title", NOTIFICATION_TITLE);
                    notificationBody.put("message", NOTIFICATION_MESSAGE);

                    notification.put("data", notificationBody);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onCreate : " + e.getMessage());
                }
                sendNotification(notification);

                break;
        }
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse : " + response.toString());
                        edit1.setText("");
                        edit2.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Test.this, "Request error", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }//메소드 끝




}
