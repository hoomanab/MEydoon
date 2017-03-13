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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.example.meydoon.EndlessScrollListener;
import com.example.meydoon.EndlessScrollListener;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.FeedListAdapter;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.data.FeedItem;
import com.example.meydoon.helper.PrefManager;

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
    private Boolean logginStatus;

    private int current_page = 1;

    private ProgressDialog pDialog;

    private Button btnLoadMore;

    private MenuItem menuItem;

    private SwipeRefreshLayout swipeRefreshLayout; /**  ===============> Implement Later <=============== */


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(getActivity().getApplicationContext());
        logginStatus = pref.isLoggedIn();

        now = System.currentTimeMillis();
    }

    @Override
    public void onStop() {
        super.onStop();

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
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_home);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh_layout);

        listView = (ListView)view.findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();


        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFeed();
            }
        });

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchFeed();
                                    }
                                }
        );
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
        fetchFeed();
    }

    private void fetchFeed(){

        swipeRefreshLayout.setRefreshing(true);

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        //getActionBar().setIcon(
        //        new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = new Cache.Entry();

        final long cacheHitButRefreshed = 3 * 60 * 1000;
        final long cacheExpired = 5 * 24 * 60 * 1000;
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
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
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

                    Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);

                    // stopping swipe refresh
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));
                //item.setShipableStatus(feedObj.getBoolean("shipable"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

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

    /*
    public void loadNextDataFromApi(int offset){
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("لطفا صبر کنید..");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        // Next page request
        URL_FEED = "http://api.androidhive.info/list_paging/?page=" + offset;

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

    }*/


    /**
     * Async Task that send a request to url
     * Gets new list view data
     * Appends to list view
     *
    public class LoadMoreListView extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();


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
    }*/
}





