package com.example.meydoon.BottomNavigation.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.meydoon.FeedImageView;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.data.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hooma on 3/17/2017.
 */
public class ContactShopFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = ContactShopFragment.class.getSimpleName();

    private LinearLayout contactTelegram, contactSms, contactCall;

    private int isShipable;
    private TextView shopName, timeStamp, productName, txtShipable, txtProductDescription, price, txtShopCity;
    private NetworkImageView shopProfilePic;
    private FeedImageView productImage;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private FeedItem feedItem;

    private Bundle extras;

    private int productId, shopId;
    private String shopTelegramId, shopPhoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_shop, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_contact_shop);



        extras = getActivity().getIntent().getExtras();

        productId = extras.getInt("product_id");
        shopTelegramId = extras.getString("shop_telegram_id");
        shopPhoneNumber = extras.getString("shop_phone_number");


        //contactTelegram = (LinearLayout) view.findViewById(R.id.contact_shop_telegram);
        contactSms = (LinearLayout) view.findViewById(R.id.contact_shop_sms);
        contactCall = (LinearLayout) view.findViewById(R.id.contact_shop_call);

        shopName = (TextView) view.findViewById(R.id.product_details_shop_name);
        timeStamp = (TextView) view.findViewById(R.id.product_details_timestamp);
        txtShopCity = (TextView) view.findViewById(R.id.product_details_feed_item_city);
        txtProductDescription = (TextView) view.findViewById(R.id.product_details_txt_status_msg);
        shopProfilePic = (NetworkImageView) view.findViewById(R.id.product_details_shop_profile_Pic);
        productImage = (FeedImageView) view.findViewById(R.id.product_details_image);
        productName = (TextView) view.findViewById(R.id.product_Details_txt_product_title);
        txtShipable = (TextView) view.findViewById(R.id.product_details_txt_shipable_status);
        price = (TextView) view.findViewById(R.id.product_details_price);

        //getProductDetails();

        shopName.setText(extras.getString("shop_name"));
        productName.setText(extras.getString("product_name"));
        price.setText(extras.getString("product_price"));

        isShipable = extras.getInt("product_shippable_status");
        if(isShipable == 1){
            txtShipable.setVisibility(View.VISIBLE);
        } else {
            txtShipable.setVisibility(View.GONE);
        }

        timeStamp.setText(extras.getString("product_register_date"));
        txtShopCity.setText(extras.getString("shop_city"));

        // Chcek for empty status message
        if (!TextUtils.isEmpty(extras.getString("product_description"))) {
            txtProductDescription.setText(extras.getString("product_description"));
            txtProductDescription.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            txtProductDescription.setVisibility(View.GONE);
        }

        shopProfilePic.setImageUrl(extras.getString("shop_picture_address"), imageLoader);

        if (extras.getString("product_picture_address") != null) {
            productImage.setImageUrl(extras.getString("product_picture_address"), imageLoader);
            productImage.setVisibility(View.VISIBLE);
            productImage
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            productImage.setVisibility(View.GONE);
        }

        contactCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent(Intent.ACTION_CALL);

                call.setData(Uri.parse("tel:" + shopPhoneNumber));
                startActivity(call);
            }
        });

        contactSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sms = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + shopPhoneNumber));
                sms.putExtra("sms_body", "سفارش محصول" + extras.getString("product_name") + "از طرف میدون");
                startActivity(sms);
            }
        });


    }

/**
    public void getProductDetails(){
        JSONObject requestProductJsonObject = new JSONObject();

        try {
            requestProductJsonObject.put("product_id", productId);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest getProductDetails = new JsonObjectRequest(Request.Method.POST, Config.URL_PRODUCT_DETAILS, requestProductJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseObj) {
                Log.d(TAG, "product details response: " + responseObj.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String error = responseObj.getString("error");
                    String message = responseObj.getString("Message");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    if (error.equals("0")) {
                        JSONArray productDetailsJA = responseObj.getJSONArray("product_details");
                        JSONObject object = (JSONObject) productDetailsJA.get(1);

                        feedItem.setProductId(object.getInt("product_id"));
                        feedItem.setShopId(object.getInt("shop_id"));
                        feedItem.setShopName(object.getString("shop_name"));

                        // Image might be null sometimes
                        String productImage = object.isNull("product_picture_address") ? null : object
                                .getString("product_picture_address");
                        feedItem.setProductImage(productImage);
                        feedItem.setProductDescription(object.getString("product_description"));


                        feedItem.setShopProfilePic(object.getString("shop_picture_address"));
                        feedItem.setProductRegisterDate(object.getString("product_register_date"));
                        //item.setShipableStatus(feedObj.getBoolean("shipable"));


                        feedItem.setProductTitle(object.getString("product_name"));
                        feedItem.setShopPhoneNumber(object.getString("shop_phone"));

                        String shopTelegramId = object.isNull("shop_telegram_id") ? null : object
                                .getString("shop_telegram_id");
                        feedItem.setShopTelegramId(shopTelegramId);

                        feedItem.setProductPrice(object.getString("product_price"));
                        feedItem.setShipableStatus(object.getInt("product_shippable_status"));
                        feedItem.setShopCity(object.getString("shop_city"));


                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "خطا در دریافت اطلاعات!",
                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error: " + volleyError.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        "خطا در برقراری ارتباط با مرکز", Toast.LENGTH_SHORT).show();
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

        getProductDetails.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getProductDetails);

    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //case R.id.contact_shop_telegram:

            //    break;

            case R.id.contact_shop_sms:

                break;

            case R.id.contact_shop_call:

                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().finish();
    }
}
