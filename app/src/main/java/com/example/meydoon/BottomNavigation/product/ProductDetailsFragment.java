package com.example.meydoon.BottomNavigation.product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.data.FeedItem;
import com.example.meydoon.helper.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hooma on 3/14/2017.
 */
public class ProductDetailsFragment extends Fragment {
    private static final String TAG = ProductDetailsFragment.class.getSimpleName();

    private Boolean isShipable;
    private TextView name, timeStamp, txtShipable, txtProductDescription, price;
    private NetworkImageView shopProfilePic;
    private FeedImageView productImage;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Button orderProduct;


    private FeedItem feedItem;
    private String URL_PRODUCT_DETAILS = Config.URL_PRODUCT_DETAILS;

    private PrefManager pref;

    private Bundle extras;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new PrefManager(getActivity().getApplicationContext());

        extras = getActivity().getIntent().getExtras();



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_item, container, false);
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.product_details);


        feedItem = new FeedItem();
        fetchFeed();

        name = (TextView) view.findViewById(R.id.name);
        timeStamp = (TextView) view.findViewById(R.id.timestamp);
        txtProductDescription = (TextView) view.findViewById(R.id.txtStatusMsg);
        shopProfilePic = (NetworkImageView) view.findViewById(R.id.profilePic);
        productImage = (FeedImageView) view.findViewById(R.id.feedImage1);
        txtShipable = (TextView) view.findViewById(R.id.txt_shipable_status);
        price = (TextView) view.findViewById(R.id.feed_price);
        orderProduct = (Button) view.findViewById(R.id.btn_feed_details);

        name.setText(feedItem.getShopName());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(feedItem.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timeStamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(feedItem.getProductDescription())) {
            txtProductDescription.setText(feedItem.getProductDescription());
            txtProductDescription.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            txtProductDescription.setVisibility(View.GONE);
        }

        shopProfilePic.setImageUrl(feedItem.getShopProfilePic(), imageLoader);

        // Feed image
        if (feedItem.getProductImage() != null) {
            productImage.setImageUrl(feedItem.getProductImage(), imageLoader);
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


        orderProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("product_id", feedItem.getProductId());
                bundle.putString("shop_phone_number", feedItem.getShopPhoneNumber());
                bundle.putString("shop_telegram_id", feedItem.getShopTelegramId());

                ContactShopFragment contactShopFragment = new ContactShopFragment();
                contactShopFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.product_details_container, contactShopFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

    }

    private void fetchFeed(){

        JSONObject requestProductJsonObject = new JSONObject();
        try {
            requestProductJsonObject.put("product_id", extras.getInt("product_id"));
        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest productDetailsJsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Config.URL_PRODUCT_DETAILS, requestProductJsonObject, new Response.Listener<JSONObject>() {
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

                        feedItem.setProductId(responseObj.getInt("product_id"));
                        feedItem.setShopId(responseObj.getInt("shop_id"));
                        feedItem.setShopName(responseObj.getString("shop_name"));

                        // Image might be null sometimes
                        String productImage = responseObj.isNull("product_image") ? null : responseObj
                                .getString("product_image");
                        feedItem.setProductImage(productImage);
                        feedItem.setProductDescription(responseObj.getString("product_description"));
                        feedItem.setShopProfilePic(responseObj.getString("shop_profile_pic"));
                        feedItem.setTimeStamp(responseObj.getString("time"));
                        //item.setShipableStatus(feedObj.getBoolean("shipable"));

                        // url might be null sometimes
                        String productTitle = responseObj.isNull("product_title") ? null : responseObj
                                .getString("product_title");
                        feedItem.setProductTitle(productTitle);
                        feedItem.setShopPhoneNumber(responseObj.getString("shop_phone_number"));
                        String shopTelegramId = responseObj.isNull("shop_telegram_id") ? null : responseObj
                                .getString("shop_telegram_id");
                        feedItem.setShopTelegramId(shopTelegramId);

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Error: " + message,
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

        productDetailsJsonObjectRequest.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(productDetailsJsonObjectRequest);
    }


    @Override
    public void onStop() {
        super.onStop();
        getActivity().finish();
    }
}
