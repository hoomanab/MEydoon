package com.example.meydoon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.meydoon.BottomNavigation.product.ProductDetailsActivity;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.data.ProfileGridItem;

import java.util.List;

/**
 * Created by hooma on 2/23/2017.
 */
public class ProfileGridAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ProfileGridItem> profileGridItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ProfileGridAdapter(Activity activity, List<ProfileGridItem> profileGridItems) {
        this.activity = activity;
        this.profileGridItems = profileGridItems;
    }

    @Override
    public int getCount() {
        return profileGridItems.size();
    }

    @Override
    public Object getItem(int location) {
        return profileGridItems.get(location);
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
            convertView = inflater.inflate(R.layout.profile_grid_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView productPrice = (TextView) convertView.findViewById(R.id.grid_product_price);
        NetworkImageView gridImageView = (NetworkImageView) convertView
                .findViewById(R.id.grid_image);

        final ProfileGridItem item = profileGridItems.get(position);

        productPrice.setText(item.getProductPrice());
        gridImageView.setImageUrl(item.getProductImage(), imageLoader);


        LinearLayout profileGridContainer = (LinearLayout) convertView.findViewById(R.id.profile_grid_container);
        profileGridContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = new Bundle();
                extras.putInt("product_id", item.getProductId());
                extras.putString("origin", "profile_grid");

                Intent prdouctDetailsIntent = new Intent(activity, ProductDetailsActivity.class);

                prdouctDetailsIntent.putExtras(extras);
                activity.startActivity(prdouctDetailsIntent);

            }
        });

        return convertView;
    }


    public void clearGridAdapter() {
        profileGridItems.clear();
        notifyDataSetChanged();
    }
}
