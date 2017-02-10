package com.example.meydoon.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.meydoon.FeedImageView;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.data.SearchItem;

import java.util.List;

/**
 * Created by hooma on 2/10/2017.
 */
public class ShopSearchResultListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<SearchItem> searchItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ShopSearchResultListAdapter(Activity activity, List<SearchItem> feedItems) {
        this.activity = activity;
        this.searchItems = feedItems;
    }


    @Override
    public int getCount() {
        return searchItems.size();
    }

    @Override
    public Object getItem(int location) {
        return searchItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.shop_search_result_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView shopName = (TextView) convertView.findViewById(R.id.shop_name);
        TextView shopCategory = (TextView) convertView
                .findViewById(R.id.shop_category);
        TextView shopCity = (TextView) convertView
                .findViewById(R.id.shop_city);
        // TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView ShopProfilePic = (NetworkImageView) convertView
                .findViewById(R.id.shopProfilePic);

        SearchItem item = searchItems.get(position);

        shopName.setText(item.getShopName());

        shopCategory.setText(item.getShopCategory());

        shopCity.setText(item.getShopCity());

        LinearLayout shopUrl = (LinearLayout) convertView.findViewById(R.id.shop_search_result_container);
        shopUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** go to shop's profile with item.getShopUrl() */
            }
        });



        // user profile pic
        ShopProfilePic.setImageUrl(item.getShopProfilePic(), imageLoader);


        return convertView;
    }
}
