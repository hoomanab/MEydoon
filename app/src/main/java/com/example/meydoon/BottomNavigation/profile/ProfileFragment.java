package com.example.meydoon.BottomNavigation.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.ProfileGridAdapter;
import com.example.meydoon.app.AppController;
import com.example.meydoon.data.ProfileGridItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hooma on 2/8/2017.
 */
public class ProfileFragment extends Fragment {
    private long now;

    private static final String TAG = ProfileFragment.class.getSimpleName();

    ImageButton profilePic;
    TextView followers, following, productCounter;
    Button btnProfileAction;

    GridView gridView;
    ProfileGridAdapter profileGridAdapter;
    List<ProfileGridItem> profileGridItems;

    private String URL_PROFILE = "https://api.myjson.com/bins/15zt0l";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        now = System.currentTimeMillis();
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

        profilePic = (ImageButton) view.findViewById(R.id.imgBtn_shop_profile);
        followers = (TextView) view.findViewById(R.id.txt_followers_number);
        following = (TextView) view.findViewById(R.id.txt_following_number);
        productCounter = (TextView) view.findViewById(R.id.txt_products_counter);

        /** Getting shop information */

        gridView = (GridView) view.findViewById(R.id.profile_grid_view);
        profileGridItems = new ArrayList<ProfileGridItem>();

        profileGridAdapter = new ProfileGridAdapter(getActivity(), profileGridItems);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = new Cache.Entry();

        final long cacheHitButRefreshed = 3 * 60 * 1000;
        final long cacheExpired = 5 * 24 * 60 * 1000;
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        entry.softTtl = softExpire;
        entry.ttl = ttl;


        entry = cache.get(URL_PROFILE);
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
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_PROFILE, null, new Response.Listener<JSONObject>() {

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

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }



        gridView.setAdapter(profileGridAdapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /** Go to ProductDetailsActivity */
            }
        });

    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonProfileGrid(JSONObject response) {
        try {
            JSONArray profileGridArray = response.getJSONArray("shopProductsGrid");

            for (int i = 0; i < profileGridArray.length(); i++) {
                JSONObject profileGridObj = (JSONObject) profileGridArray.get(i);

                ProfileGridItem item = new ProfileGridItem();
                item.setProductId(profileGridObj.getInt("productId"));
                // Image might be null sometimes
                String image = profileGridObj.isNull("productImage") ? null : profileGridObj
                        .getString("productImage");
                item.setProductImage(image);
                item.setProductPrice(profileGridObj.getString("productPrice"));

                profileGridItems.add(item);
            }

            // notify data changes to list adapater
            profileGridAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
