package com.example.meydoon.BottomNavigation.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meydoon.R;
import com.example.meydoon.adapter.NotificationInboxAdapter;
import com.example.meydoon.app.AppController;
import com.example.meydoon.data.NotificationInboxItem;
import com.example.meydoon.helper.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hooma on 3/1/2017.
 */
public class ProfileNotificationsFragment extends Fragment {
    private static final String TAG = ProfileNotificationsFragment.class.getSimpleName();

    private ListView listView;
    private NotificationInboxAdapter notificationInboxAdapter;
    private List<NotificationInboxItem> notificationInboxItems;

    private String URL_NOTIFICATION = "https://api.myjson.com/bins/1ezj9d";

    private PrefManager pref;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new PrefManager(getActivity().getApplicationContext());

        //pref.checkLogin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_notification, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** Custom Action Bar*/
        ((ProfileNotificationsActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((ProfileNotificationsActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((ProfileNotificationsActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_profile_notification);



        listView = (ListView) view.findViewById(R.id.profile_notifications_list);

        notificationInboxItems = new ArrayList<NotificationInboxItem>();


        notificationInboxAdapter = new NotificationInboxAdapter(getActivity(), notificationInboxItems);
        listView.setAdapter(notificationInboxAdapter);


        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_NOTIFICATION);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonNotification(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_NOTIFICATION, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonNotification(response);
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
    private void parseJsonNotification(JSONObject response) {
        try {
            JSONArray notificationArray = response.getJSONArray("notification");

            for (int i = 0; i < notificationArray.length(); i++) {
                JSONObject notificationObject = (JSONObject) notificationArray.get(i);

                NotificationInboxItem item = new NotificationInboxItem();
                item.setNotificationId(notificationObject.getInt("notificationId"));
                item.setShopName(notificationObject.getString("shopName"));

                item.setShopProfilePic(notificationObject.getString("shopProfilePic"));
                item.setNotificationTime(notificationObject.getString("time"));
                item.setNotificationMessageTitle(notificationObject.getString("notificationMessageTile"));
                item.setNotificationMessage(notificationObject.getString("notificationMessage"));
                notificationInboxItems.add(item);
            }

            // notify data changes to list adapater
            notificationInboxAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void clickEvent(View view){
        switch (view.getId()){

        }

    }


}
