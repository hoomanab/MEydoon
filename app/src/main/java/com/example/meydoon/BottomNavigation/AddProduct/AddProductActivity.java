package com.example.meydoon.BottomNavigation.AddProduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.helper.PrefManager;
import com.example.meydoon.receiver.ConnectivityReceiver;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by hooma on 2/19/2017.
 */
public class AddProductActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private static String TAG = AddProductActivity.class.getSimpleName();

    private PrefManager pref;

    private TextView txtNoConnection;

    private JSONObject requestShopIdJS;



    int shopId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(getApplicationContext());
        pref.checkLogin();



        /** Get user's shopID from server */
        getShopId();

        //pref.setShopId(shopId);

        setContentView(R.layout.add_product_fragment);

        txtNoConnection = (TextView) findViewById(R.id.add_product_txt_no_internet);
        checkConnection();

        /** Check if the user has a shop */
        if(pref.getShopId() != 0){
            // Check that the activity is using the layout version with
            // the fragment_container FrameLayout
            if (findViewById(R.id.add_product_container) != null) {

                // However, if we're being restored from a previous state,
                // then we don't need to do anything and should return or else
                // we could end up with overlapping fragments.
                if (savedInstanceState != null) {
                    return;
                }

                // Create a new Fragment to be placed in the activity layout
                AddProductFragment goToAddProduct = new AddProductFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                goToAddProduct.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.add_product_container, goToAddProduct).commit();
            }
        } else {
            ShopRegisterFragment shopRegisterFragment = new ShopRegisterFragment();
            shopRegisterFragment.setArguments(getIntent().getExtras());

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.add_product_container, shopRegisterFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


    }

    public void getShopId(){

        requestShopIdJS = new JSONObject();
        try {

            requestShopIdJS.put("user_id", pref.getUserId());

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Config.URL_GET_SHOP_ID, requestShopIdJS, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    // Parsing json object response
                    // response will be a json object
                    String error = jsonObject.getString("error");
                    //String message = jsonObject.getString("Message");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    if (error.equals("0")) {
                        // boolean flag saying device is waiting for sms
                        pref.setShopId(jsonObject.getInt("shop_id"));



                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Error: " + "unable to get shop_id",
                                Toast.LENGTH_LONG).show();
                    }



                } catch (JSONException e) {
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
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }
        };


        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public int retrieveShopId (){
        getShopId();
        return shopId;
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showNoConnection(isConnected);
    }

    private void showNoConnection(boolean isConnected){
        if(isConnected) {
            txtNoConnection.setVisibility(View.GONE);
        } else{
            txtNoConnection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v(TAG, "MainActivity resumed!");

        checkConnection();
        AppController.getInstance().setConnectivityListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        //finish();
    }


    // Kill app on back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showNoConnection(isConnected);
    }

    public void setActionBarTitle(String title){
        getActionBar().setTitle(title);
    }


    /** Handling clicks on actionbar icons */
    public void addProductClickEvent(View view){
        switch (view.getId()){

            /** For shop add product actionbar,
             * @param img_abort_add_product**/
            case R.id.img_abort_add_product:
                finish();
                break;

            case R.id.img_abort_shop_register:
                finish();
                break;


        }
    }


}
