package com.example.meydoon.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meydoon.MainActivity;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.helper.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hooma on 2/21/2017.
 */
public class VerifyOtpHttpService extends IntentService {
    // Session Manager Class
    PrefManager pref;


    private static String TAG = VerifyOtpHttpService.class.getSimpleName();

    public VerifyOtpHttpService() {
        super(VerifyOtpHttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Bundle extras = intent.getExtras();

            String otp = extras.getString("otp");
            String user_phone_number = extras.getString("user_phone_number");

            verifyOtp(otp, user_phone_number);
        }
    }

    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(final String otp, final String user_phone_number) {
        JSONObject verificationJsonObj = new JSONObject();
        try {
            verificationJsonObj.put("user_phone_number", user_phone_number);
            verificationJsonObj.put("user_phone_number_validation_code", otp);

        }catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                Config.URL_VERIFY_OTP, verificationJsonObj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    // Parsing json object response
                    // response will be a json object
                    boolean error = response.getBoolean("error");
                    String message = response.getString("Message");

                    //if (!error) {
                        // parsing the user profile information



                    int user_id = response.getInt("user_id");
                    String user_mobile_mobile = response.getString("user_phone_number");
                    String user_name = response.getString("user_name");

                    /**  =================> Need to continue from here <=================**/

                    pref = new PrefManager(getApplicationContext());
                    pref.createLogin(user_id,user_name,user_mobile_mobile);
                    //pref.createLogin(mobile);

                    Intent intent = new Intent(VerifyOtpHttpService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                   // } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                   // }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", otp);

                Log.e(TAG, "Posting params: " + params.toString());
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
}
