package com.example.meydoon.BottomNavigation.product;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Product Details Activity
 */
public class ProductDetailsActivity extends AppCompatActivity {
    private static final String TAG = ProductDetailsActivity.class.getSimpleName();

    private Bundle extras;

    private int productId;

    private String shopTelegramId, shopPhoneNumber;

    private Boolean successfullConnection;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.product_details_fragment);

        savedInstanceState = getIntent().getExtras();

        if(savedInstanceState.getString("origin").equals("profile_grid")){
            ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();

            extras = new Bundle();
            extras.putInt("product_id", savedInstanceState.getInt("product_id"));
            productDetailsFragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction().add(R.id.product_details_container, productDetailsFragment).commit();
        } else if (savedInstanceState.getString("origin").equals("home_fragment")){
            ContactShopFragment contactShopFragment = new ContactShopFragment();

            extras = new Bundle();
            extras.putInt("product_id", savedInstanceState.getInt("product_id"));
            extras.putString("shop_telegram_id", savedInstanceState.getString("shop_telegram_id"));
            extras.putString("shop_phone_number", savedInstanceState.getString("shop_phone_number"));
            contactShopFragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction().add(R.id.product_details_container, contactShopFragment).commit();
        } else {
            Toast.makeText(getApplicationContext(), "خطا در برقراری ارتباط با فروشگاه!", Toast.LENGTH_LONG).show();
            finish();
        }



    }

    /*public void getShopId(){
        JSONObject productIdJsonObject = new JSONObject();
        try {
            productIdJsonObject.put("product_id", productId);
        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest requestProductOwnerId = new JsonObjectRequest(Request.Method.POST,
                Config.URL_GET_SHOP_CONTACT_INFO, productIdJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseObj) {
                Log.d(TAG, responseObj.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String error = responseObj.getString("error");
                    String message = responseObj.getString("Message");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    if (error.equals("0")) {
                        successfullConnection = true;
                        shopTelegramId = responseObj.getString("shop_telegram_id");
                        shopPhoneNumber = responseObj.getString("shop_phone_number");


                    } else {
                        successfullConnection = false;
                        Toast.makeText(getApplicationContext(),
                                "Error: " + message,
                                Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e){
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(),
                        volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }
        };

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        requestProductOwnerId.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(requestProductOwnerId);
    }*/

    @Override
    protected void onStop() {
        super.onStop();

        finish();
    }


    /** Handling clicks on actionbar icons */
    public void productDetailsClickEvent(View view){
        switch (view.getId()){

            /** For product details,
             * @param img_product_details_back
             * @param img_contact_shop_back**/

            case R.id.img_product_details_back:
                finish();
                break;
            case R.id.img_contact_shop_back:
                finish();
                break;

        }
    }
}
