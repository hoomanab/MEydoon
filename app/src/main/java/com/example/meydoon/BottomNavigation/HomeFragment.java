package com.example.meydoon.BottomNavigation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.example.meydoon.EndlessScrollListener;
import com.example.meydoon.BottomNavigation.AddProduct.AddProductActivity;
import com.example.meydoon.EndlessScrollListener;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.FeedListAdapter;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.data.FeedItem;
import com.example.meydoon.helper.PrefManager;
import com.example.meydoon.receiver.ConnectivityReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * To handle the view when Home icon is selected from bottom navigation!
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private long now;

    private BottomNavigationView bottomNavigationView;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = Config.URL_HOME_FEED;

    private PrefManager pref;
    private Boolean logginStatus, allowScroll;

    private int current_page;
    private int mCurCheckPosition;

    private Bundle savedState = null;

    private Button btnLoadMore;

    private MenuItem menuItem;

    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * ===============> Implement Later <===============
     */

    private Cache cache;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cache = AppController.getInstance().getRequestQueue().getCache();
        allowScroll = true;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ConnectivityReceiver.isConnected()) {
            cache.clear();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /** Custom Action Bar*/
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_home);

        pref = new PrefManager(getActivity().getApplicationContext());
        logginStatus = pref.isLoggedIn();

        now = System.currentTimeMillis();

        /*if (pref.isLoggedIn()) {
            AddProductActivity addProductActivity = new AddProductActivity();
            addProductActivity.getShopId();
        }*/
        current_page = 1;

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh_layout);

        listView = (ListView) view.findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();


        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);

        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(this);


        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //listView.setAdapter(null);
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchFeed();
                                    }
                                }
        );


        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (allowScroll) {
                    loadNextDataFromApi(current_page + 1);
                }
            }
        });
/*
        btnLoadMore = new Button(getActivity());
        btnLoadMore.setText("بیشتر");
        listView.addFooterView(btnLoadMore);
        btnLoadMore.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
        btnLoadMore.setPadding(10, 10, 10, 10);*/

        /** ========> If logginStatus == false then
         *              show Home for Guest
         *
         *            else
         *              pref.checkLogin();
         *              HashMap<String, String> user = getUserDetails();
         *
         *              String user_id = user.get(PrefManager.KEY_ID);
         *
         *              Then send user_id as JSONObject to server and get it's latest following shops products!**/




       /* btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadMoreListView().execute();
            }
        });*/

        // listView.setOnScrollListener(new EndlessScrollListener() {
        //     @Override
        //     public boolean onLoadMore(int page, int totalItemsCount) {
        //new LoadMoreListView().execute();
        //return true;
        //    }
        //  });


    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        if (ConnectivityReceiver.isConnected()) {
            cache.clear();

        }
        //listAdapter.clearFeedAdapter();
        //swipeRefreshLayout.setRefreshing(true);
        fetchFeed();
    }

    private void fetchFeed() {


        swipeRefreshLayout.setRefreshing(true);

        JSONObject feedJsonObject = new JSONObject();
        try {
            feedJsonObject.put("user_id", pref.getUserId());
            feedJsonObject.put("page_number", 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        //getActionBar().setIcon(
        //        new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request


        Cache.Entry entry = new Cache.Entry();

        final long cacheHitButRefreshed = 10 * 1000;
        final long cacheExpired = 24 * 60 * 60 * 1000;
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        entry.softTtl = softExpire;
        entry.ttl = ttl;

        entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    refreshParseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {


            // making fresh volley request and getting json
            JsonObjectRequest requestFeed = new JsonObjectRequest(Request.Method.POST,
                    URL_FEED, feedJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    try {
                        // Parsing json object response
                        // response will be a json object
                        String error = response.getString("error");

                        // checking for error, if not error SMS is initiated
                        // device should receive it shortly
                        if (error.equals("0")) {
                            refreshParseJsonFeed(response);
                        } else if (error.equals("1")) {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "محصولی وجود ندارد!",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "خطا در دریافت اطلاعات!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(),
                            "خطا در دریافت اطلاعات. ممکنه به اینترنت متصل نباشید!", Toast.LENGTH_SHORT).show();

                    // stopping swipe refresh
                    swipeRefreshLayout.setRefreshing(false);
                }
            }) {

                @Override
                public String getBodyContentType() {
                    return String.format("application/json; charset=utf-8");
                }

            };


            int socketTimeout = 10000; // 10 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

            requestFeed.setRetryPolicy(policy);

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(requestFeed);
        }
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("Feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject responseObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setProductId(responseObj.getInt("product_id"));
                item.setShopId(responseObj.getInt("shop_id"));
                item.setShopName(responseObj.getString("shop_name"));

                // Image might be null sometimes
                String productImage = responseObj.isNull("product_picture_address") ? null : responseObj
                        .getString("product_picture_address");
                item.setProductImage(productImage);
                item.setProductDescription(responseObj.getString("product_description"));

                String shopImage = responseObj.isNull("shop_picture_address") ? null : responseObj
                        .getString("shop_picture_address");
                item.setShopProfilePic(responseObj.getString("shop_picture_address"));
                item.setProductRegisterDate(responseObj.getString("product_register_date"));
                //item.setShipableStatus(feedObj.getBoolean("shipable"));

                // url might be null sometimes
                //String productTitle = responseObj.isNull("product_name") ? null : responseObj
                //      .getString("product_name");
                item.setProductTitle(responseObj.getString("product_name"));
                item.setShopPhoneNumber(responseObj.getString("shop_phone"));
                String shopTelegramId = responseObj.isNull("shop_telegram_id") ? null : responseObj
                        .getString("shop_telegram_id");
                item.setShopTelegramId(shopTelegramId);
                item.setProductPrice(responseObj.getString("product_price"));
                item.setShipableStatus(responseObj.getInt("product_shippable_status"));
                item.setShopCity(responseObj.getString("shop_city"));


                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();

            // stopping swipe refresh
            //swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Parsing json reponse and passing the data to feed view list adapter
     */
    private void refreshParseJsonFeed(JSONObject response) {
        try {
            listAdapter.clearFeedAdapter();

            JSONArray feedArray = response.getJSONArray("Feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject responseObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setProductId(responseObj.getInt("product_id"));
                item.setShopId(responseObj.getInt("shop_id"));
                item.setShopName(responseObj.getString("shop_name"));

                // Image might be null sometimes
                String productImage = responseObj.isNull("product_picture_address") ? null : responseObj
                        .getString("product_picture_address");
                item.setProductImage(productImage);
                item.setProductDescription(responseObj.getString("product_description"));

                String shopImage = responseObj.isNull("shop_picture_address") ? null : responseObj
                        .getString("shop_picture_address");
                item.setShopProfilePic(responseObj.getString("shop_picture_address"));
                item.setProductRegisterDate(responseObj.getString("product_register_date"));
                //item.setShipableStatus(feedObj.getBoolean("shipable"));

                // url might be null sometimes
                //String productTitle = responseObj.isNull("product_name") ? null : responseObj
                //      .getString("product_name");
                item.setProductTitle(responseObj.getString("product_name"));
                item.setShopPhoneNumber(responseObj.getString("shop_phone"));
                String shopTelegramId = responseObj.isNull("shop_telegram_id") ? null : responseObj
                        .getString("shop_telegram_id");
                item.setShopTelegramId(shopTelegramId);
                item.setProductPrice(responseObj.getString("product_price"));
                item.setShipableStatus(responseObj.getInt("product_shippable_status"));
                item.setShopCity(responseObj.getString("shop_city"));


                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();

            // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void loadNextDataFromApi(int offset) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("لطفا صبر کنید..");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();


        /** Begin ************************************/
        JSONObject feedJsonObject = new JSONObject();
        try {
            feedJsonObject.put("user_id", pref.getUserId());
            feedJsonObject.put("page_number", offset);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // making fresh volley request and getting json
        JsonObjectRequest requestFeed = new JsonObjectRequest(Request.Method.POST,
                URL_FEED, feedJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                try {
                    // Parsing json object response
                    // response will be a json object
                    String error = response.getString("error");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    if (error.equals("0")) {
                        parseJsonFeed(response);
                        pDialog.hide();
                    } else if (error.equals("1")) {
                        allowScroll = false;
                        pDialog.hide();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "خطا در دریافت اطلاعات!",
                            Toast.LENGTH_LONG).show();
                    pDialog.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        "خطا در دریافت اطلاعات ", Toast.LENGTH_SHORT).show();
                pDialog.hide();

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

    @Override
    public void onPause() {
        super.onPause();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.destroyDrawingCache();
            swipeRefreshLayout.clearAnimation();
        }
    }

    /**
     * Async Task that send a request to url
     * Gets new list view data
     * Appends to list view
     **/
    public class LoadMoreListView extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            /* Showing progress dialog before sending http request
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        protected Void doInBackground(Void... unused) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    // increment current page
                    current_page += 1;

                    // Next page request
                    URL_FEED = "http://api.androidhive.info/list_paging/?page=" + current_page;

                    // making fresh volley request and getting json
                    JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                            URL_FEED, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            VolleyLog.d(TAG, "Response: " + response.toString());
                            if (response != null) {
                                parseJsonFeed(response);
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                        }
                    });

                    // Adding request to volley request queue
                    AppController.getInstance().addToRequestQueue(jsonReq);

                    // get listview current position - used to maintain scroll position
                    int currentPosition = listView.getFirstVisiblePosition();

                    // Setting new scroll position
                    listView.setSelectionFromTop(currentPosition + 1, 0);

                }
            });
            return (null);
        }

        protected void onPostExecute(Void unused) {
            // closing progress dialog
            //            pDialog.dismiss();
        }
    }
}





