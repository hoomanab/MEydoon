package com.example.meydoon.BottomNavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
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
import com.example.meydoon.app.Config;
import com.example.meydoon.data.FeedItem;
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


    private String URL_SHOP_SEARCH = Config.URL_SEARCH;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //View view = ((MainActivity)getActivity()).getSupportActionBar().getCustomView();
        //ImageButton imageButton= (ImageButton)view.findViewById(R.id.meydoon_tab);

        //imageButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {


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
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /** Custom Action Bar*/
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_home);


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






        //shopSearchResultListAdapter = new ShopSearchResultListAdapter(getActivity(), searchItems);


        // We first check for cached request
        //Cache cache = AppController.getInstance().getRequestQueue().getCache();
        //Cache.Entry entry = cache.get(URL_SHOP_SEARCH);
        /*if (entry != null) {
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
            AppController.getInstance().addToRequestQueue(jsonReq);*/
        //}
        listView = (ListView) view.findViewById(R.id.shop_search_result_list_view);
        searchItems = new ArrayList<SearchItem>();

        shopSearchResultListAdapter = new ShopSearchResultListAdapter(getActivity(), searchItems);

        JsonReq();
        /** Search box **/
        final EditText search = (EditText) view.findViewById(R.id.inputSearch);
        if(search.getText().length() >= 3){

        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ShopSearchResultListAdapter filteredAdapter = shopSearchResultListAdapter;

                filteredAdapter.getFilter().filter(charSequence);
                //shopSearchResultListAdapter = new ShopSearchResultListAdapter(getActivity(), searchItems);

                listView.setAdapter(filteredAdapter);


                //shopSearchResultListAdapter = new ShopSearchResultListAdapter(getActivity(), searchItems);
                //listView.setAdapter(shopSearchResultListAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        search.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void JsonReq(){

        //final String cs = charSequence.toString().toLowerCase();
        //ArrayList<SearchItem> filteredItem = shopSearchResultListAdapter.getFilter(charSequence);
        //shopSearchResultListAdapter = new ShopSearchResultListAdapter(getActivity(), filteredItem);
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
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonShopSearch(JSONObject response) {
        try {
            JSONArray shopSearchArray = response.getJSONArray("search");

            for (int i = 0; i < shopSearchArray.length(); i++) {
                JSONObject shopSearchObj = (JSONObject) shopSearchArray.get(i);

                SearchItem item = new SearchItem(shopSearchObj.getInt("shopId"), shopSearchObj.getString("shopName"), shopSearchObj.getString("shopCategory"), shopSearchObj.getString("shopProfilePic"), shopSearchObj.getString("shopCity"), shopSearchObj
                        .getString("shopUrl"));
                /*item.setShopCategory(shopSearchObj.getString("shopCategory"));
                item.setShopId(shopSearchObj.getInt("shopId"));

                item.setShopName(shopSearchObj.getString("shopName"));

                System.out.println("اینجا!");

                item.setShopProfilePic(shopSearchObj.getString("shopProfilePic"));
                item.setShopCity(shopSearchObj.getString("shopCity"));

                // url might be null sometimes
                String shopUrl = shopSearchObj.isNull("shopUrl") ? null : shopSearchObj
                        .getString("url");
                item.setShopUrl(shopUrl);*/

                searchItems.add(item);
            }

            // notify data changes to list adapater
            shopSearchResultListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
