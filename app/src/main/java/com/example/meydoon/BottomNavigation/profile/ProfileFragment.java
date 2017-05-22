package com.example.meydoon.BottomNavigation.profile;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.meydoon.EndlessScrollListener;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.ProfileGridAdapter;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.data.ProfileGridItem;
import com.example.meydoon.data.ShopInfoItem;
import com.example.meydoon.helper.ExpandableHeightGridView;
import com.example.meydoon.helper.PrefManager;
import com.example.meydoon.receiver.ConnectivityReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hooma on 2/8/2017.
 */
public class ProfileFragment extends Fragment  {
    private long now;

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private ImageButton profilePic;
    private TextView followers, following, productCounter, shopName,
            shopAddress,shopCity, shopCategoryName, shopDescription;
    private Button btnProfileAction;
    private NetworkImageView shopProfilePic;

    private ExpandableHeightGridView gridView;
    private ProfileGridAdapter profileGridAdapter;
    private List<ProfileGridItem> profileGridItems;

    private Cache cache;

    //private SwipeRefreshLayout swipeRefreshLayout;

    private int currentPage;
    private Boolean logginStatus, viewerIsFollower, viewerIsOwner;

    private PrefManager pref;

    private Bundle reveivedExtras;

    private String shopTelegragmId,
            shopRegisterDate, shopPhoneNumber;
    private int shopOwnerUserId, shopFollowerCounter, shopId, shopProductCounter, shopIsVerified, socketTimeout;
    private ShopInfoItem shopInfoItem;

    private RetryPolicy policy;

    private String getShopInfoUrl = Config.URL_SHOP_PROFILE_INFO;
    private String getShopProductsUrl = Config.URL_SHOP_PROFILE_PRODUCTS;

    private GradientDrawable colorForbutton;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        socketTimeout = 10000; // 30 seconds. You can change it
        policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        reveivedExtras = this.getArguments();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_main, container, false);
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** Custom Action Bar*/
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_shop_profile);



        pref = new PrefManager(getActivity().getApplicationContext());
        logginStatus = pref.isLoggedIn();

        now = System.currentTimeMillis();
        cache = AppController.getInstance().getRequestQueue().getCache();

        shopInfoItem = new ShopInfoItem();


        followers = (TextView) view.findViewById(R.id.profile_txt_followers_number);
        //following = (TextView) view.findViewById(R.id.profile_txt_following_number);
        productCounter = (TextView) view.findViewById(R.id.profile_txt_products_counter);
        shopName = (TextView) view.findViewById(R.id.profile_shop_name_txt);
        shopCity = (TextView) view.findViewById(R.id.profile_txt_shop_city);
        shopCategoryName = (TextView) view.findViewById(R.id.txt_shop_category);
        shopDescription = (TextView) view.findViewById(R.id.profile_txt_shop_description);
        shopAddress = (TextView) view.findViewById(R.id.profile_txt_shop_address);
        shopProfilePic = (NetworkImageView) view.findViewById(R.id.shop_profile_pic);
        btnProfileAction = (Button) view.findViewById(R.id.profile_action_btn);



        //swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.profile_swipe_refresh_layout);

        /** Getting shop information */

        gridView = (ExpandableHeightGridView) view.findViewById(R.id.profile_grid_view);
        gridView.setExpanded(true);

        profileGridItems = new ArrayList<ProfileGridItem>();

        profileGridAdapter = new ProfileGridAdapter(getActivity(), profileGridItems);

        gridView.setAdapter(profileGridAdapter);

        fetchData();







        btnProfileAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadNextDataFromApi(currentPage + 1);
            }
        });



        //swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //listView.setAdapter(null);
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchData();
                                    }
                                }
        );*/


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /** Go to ProductDetailsActivity */

            }
        });

    }



    public void fetchData() {

        profileGridAdapter.clearGridAdapter();
        //swipeRefreshLayout.setRefreshing(true);

        JSONObject profileInfoJsonObject = new JSONObject();
        try {
            profileInfoJsonObject.put("user_id", pref.getUserId());
            profileInfoJsonObject.put("shop_id", reveivedExtras.getInt("shop_id"));

        } catch (JSONException e){
            e.printStackTrace();
        }



        Cache.Entry entry = new Cache.Entry();

        final long cacheHitButRefreshed = 3 * 60 * 1000;
        final long cacheExpired = 5 * 24 * 60 * 1000;
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        entry.softTtl = softExpire;
        entry.ttl = ttl;


        entry = cache.get(getShopInfoUrl);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonShopInfo(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest profileInfoJsonRequest = new JsonObjectRequest(Request.Method.POST,
                    getShopInfoUrl, profileInfoJsonObject, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonShopInfo(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });




            profileInfoJsonRequest.setRetryPolicy(policy);

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(profileInfoJsonRequest);
        }


        JSONObject profileProductsJsonObject = new JSONObject();
        try {
            profileProductsJsonObject.put("page_number", 1);
            profileProductsJsonObject.put("shop_id", reveivedExtras.getInt("shop_id"));
            profileProductsJsonObject.put("user_id", pref.getUserId());

        }catch (JSONException e){
            e.printStackTrace();
        }


        entry = cache.get(getShopProductsUrl);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonProfileGrid(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {




            // making fresh volley request and getting json
            JsonObjectRequest getShopProductsJsonReq = new JsonObjectRequest(Request.Method.POST,
                    getShopProductsUrl, profileProductsJsonObject, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonProfileGrid(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });


            //swipeRefreshLayout.setRefreshing(false);

            getShopProductsJsonReq.setRetryPolicy(policy);
            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(getShopProductsJsonReq);
        }
    }



    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonShopInfo(JSONObject responseObj) {
        try {

            String error = responseObj.getString("error");


            // checking for error, if not error SMS is initiated
            // device should receive it shortly
            if (error.equals("0")) {
                JSONArray profileInfoJA = responseObj.getJSONArray("shop_profile");
                JSONObject profileInfoJsonObject = (JSONObject) profileInfoJA.get(0);

                shopInfoItem.setShopAddress(profileInfoJsonObject.getString("shop_address"));
                shopInfoItem.setShopCity(profileInfoJsonObject.getString("shop_city"));
                shopInfoItem.setViewerIsOwnerStatus(profileInfoJsonObject.getBoolean("is_owner"));
                shopInfoItem.setShopTelegramId(profileInfoJsonObject.getString("shop_telegram_id"));
                shopInfoItem.setShopRegisterDate(profileInfoJsonObject.getString("shop_reg_date"));
                shopInfoItem.setShopPhoneNumber(profileInfoJsonObject.getString("shop_phone"));
                shopInfoItem.setShopUserId(profileInfoJsonObject.getInt("user_table_user_id"));
                shopInfoItem.setShopCategoryName(profileInfoJsonObject.getString("shop_category_name"));
                shopInfoItem.setShopProfilePic(profileInfoJsonObject.getString("shop_picture_address"));
                shopInfoItem.setShopName(profileInfoJsonObject.getString("shop_name"));
                shopInfoItem.setShopFollowerCounter(profileInfoJsonObject.getInt("shop_followers_count"));
                shopInfoItem.setShopId(profileInfoJsonObject.getInt("shop_id"));
                shopInfoItem.setShopProductCounter(profileInfoJsonObject.getInt("shop_product_counter"));
                if (profileInfoJsonObject.getBoolean("is_owner") == true) {
                    shopInfoItem.setViewerIsFollowerStatus(true);
                } else {
                    shopInfoItem.setViewerIsFollowerStatus(profileInfoJsonObject.getBoolean("has_followed"));
                }

                shopInfoItem.setShopDescription(profileInfoJsonObject.getString("shop_description"));
                shopInfoItem.setShopIsVerified(profileInfoJsonObject.getInt("is_verified"));


                setProfileInfo();

            } else {
                Toast.makeText(getActivity().getApplicationContext(),
                        "خطا در دریافت اطلاعات!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setProfileInfo() {


        followers.setText("" + shopInfoItem.getShopFollowerCounter());
        productCounter.setText("" + shopInfoItem.getShopProductCounter());
        shopName.setText(shopInfoItem.getShopName());
        shopCity.setText(shopInfoItem.getShopCity());
        shopCategoryName.setText(shopInfoItem.getShopCategoryName());
        shopDescription.setText(shopInfoItem.getShopDescription());
        shopAddress.setText("آدرس: "  + shopInfoItem.getShopAddress());
        shopProfilePic.setImageUrl(shopInfoItem.getShopProfilePic(), imageLoader);

        colorForbutton = new GradientDrawable();

        if (shopInfoItem.getViewerIsOwnerStatus()) {
            btnProfileAction.setText("ویرایش");

            colorForbutton.setColor(getResources().getColor(R.color.white)); // Changes this drawbale to use a single color instead of a gradient
            colorForbutton.setCornerRadius(3);
            colorForbutton.setStroke(1, getResources().getColor(R.color.black));
            btnProfileAction.setTextColor(getResources().getColor(R.color.black));

            btnProfileAction.setBackgroundDrawable(colorForbutton);
        }
        if (!(shopInfoItem.getViewerIsOwnerStatus()) && shopInfoItem.getviewerIsFollowerStatus()) {
            btnProfileAction.setText("انصراف از دنبال کردن");

            colorForbutton.setColor(getResources().getColor(R.color.white)); // Changes this drawbale to use a single color instead of a gradient
            colorForbutton.setCornerRadius(3);
            colorForbutton.setStroke(1, getResources().getColor(R.color.black));
            btnProfileAction.setTextColor(getResources().getColor(R.color.black));
                    }
        if (!(shopInfoItem.getViewerIsOwnerStatus()) && !(shopInfoItem.getviewerIsFollowerStatus())) {
            btnProfileAction.setText("دنبال کن");

            colorForbutton.setColor(getResources().getColor(R.color.blue)); // Changes this drawbale to use a single color instead of a gradient
            colorForbutton.setCornerRadius(3);
            colorForbutton.setStroke(1, getResources().getColor(R.color.black));
            btnProfileAction.setTextColor(getResources().getColor(R.color.white));

            btnProfileAction.setBackgroundDrawable(colorForbutton);
        }
    }


    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonProfileGrid(JSONObject response) {
        try {
            JSONArray profileGridArray = response.getJSONArray("product_details");

            for (int i = 0; i < profileGridArray.length(); i++) {
                JSONObject profileGridObj = (JSONObject) profileGridArray.get(i);

                ProfileGridItem item = new ProfileGridItem();
                item.setProductId(profileGridObj.getInt("product_id"));
                // Image might be null sometimes
                String image = profileGridObj.isNull("product_picture_address") ? null : profileGridObj
                        .getString("product_picture_address");
                item.setProductImage(image);
                item.setProductPrice(profileGridObj.getString("product_price"));

                profileGridItems.add(item);
            }

            // notify data changes to list adapater
            profileGridAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void loadNextDataFromApi(int offset){
        /*pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("لطفا صبر کنید..");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();*/





        /** Begin ************************************/
        JSONObject profileGridJsonObject = new JSONObject();
        try {
            profileGridJsonObject.put("user_id", pref.getUserId());
            profileGridJsonObject.put("page_number", offset);
            profileGridJsonObject.put("shop_id", reveivedExtras.getInt("shop_id"));


        }catch (JSONException e){
            e.printStackTrace();
        }

        // making fresh volley request and getting json
        JsonObjectRequest requestFeed = new JsonObjectRequest(Request.Method.POST,
                Config.URL_SHOP_PROFILE_PRODUCTS, profileGridJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonProfileGrid(response);
                    //pDialog.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        "خطا در دریافت اطلاعات ", Toast.LENGTH_SHORT).show();

                //pDialog.hide();
            }
        }) {


            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

        };


        int socketTimeout = 10000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        requestFeed.setRetryPolicy(policy);

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(requestFeed);

        /** End **************************************/



        // get listview current position - used to maintain scroll position
        //int currentPosition = listView.getFirstVisiblePosition();

        // Setting new scroll position
        //listView.setSelectionFromTop(currentPosition + 1, 0);

    }


}
