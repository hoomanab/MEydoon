package com.example.meydoon.BottomNavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.ShopSearchResultListAdapter;
import com.example.meydoon.app.AppController;
import com.example.meydoon.data.SearchItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hooma on 2/8/2017.
 */
public class SearchFragment extends Fragment {
    private static final String TAG = SearchFragment.class.getSimpleName();
    private ListView listView;
    private ShopSearchResultListAdapter shopSearchResultListAdapter;
    private List<SearchItem> searchItems;


    private String URL_SHOP_SEARCH = "https://api.myjson.com/bins/wg40t";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //View view = ((MainActivity)getActivity()).getSupportActionBar().getCustomView();
        //ImageButton imageButton= (ImageButton)view.findViewById(R.id.meydoon_tab);

        //imageButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
                /** We should make it act like a tab
                 *  Note: After adding other icons to the action bar, by clicking on them, main_container must change!*/

         //   }
        //});
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /** Custom Action Bar*/
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.search_actionbar);
        /** Search box **/
        final EditText search = (EditText) view.findViewById(R.id.inputSearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String results = search.getText().toString().toLowerCase(Locale.getDefault());

            }
        });


/*
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /* * ****************************************performSearch();**************************
                    //shopSearch(textView);
                    return true;
                }
                return false;
            }
        });
*/

        listView = (ListView) view.findViewById(R.id.shop_search_result_list_view);

        searchItems = new ArrayList<SearchItem>();


        shopSearchResultListAdapter = new ShopSearchResultListAdapter(getActivity(), searchItems);
        listView.setAdapter(shopSearchResultListAdapter);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_SHOP_SEARCH);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonShopSearch(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_SHOP_SEARCH, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonShopSearch(response);
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

    }



    /**
     * Parsing json reponse and passing the data to shop search results view list adapter
     * */
    private void parseJsonShopSearch(JSONObject response) {
        try {
            JSONArray shopResultSearchArray = response.getJSONArray("shopSearch");

            for (int i = 0; i < shopResultSearchArray.length(); i++) {
                JSONObject shopSearchObject = (JSONObject) shopResultSearchArray.get(i);

                SearchItem item = new SearchItem();
                item.setShopId(shopSearchObject.getInt("shopId"));
                item.setShopName(shopSearchObject.getString("shopName"));
                item.setShopCategory(shopSearchObject.getString("shopCategory"));
                item.setShopProfilePic(shopSearchObject.getString("shopProfilePic"));
                item.setShopCity(shopSearchObject.getString("shopCity"));

                // url might be null sometimes
                String shopSearchUrl = shopSearchObject.isNull("shopUrl") ? null : shopSearchObject
                        .getString("shopUrl");
                item.setShopUrl(shopSearchUrl);

                searchItems.add(item);
            }

            // notify data changes to list adapater
            shopSearchResultListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
