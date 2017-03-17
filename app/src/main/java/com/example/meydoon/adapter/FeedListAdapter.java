package com.example.meydoon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.meydoon.BottomNavigation.product.ProductDetailsActivity;
import com.example.meydoon.FeedImageView;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.data.FeedItem;

import java.util.List;

/**
 * Displaying feed data like name, timestamp, profile pic, status message and feed image.
 * Converts timestamp into x minutes/hours/days ago format
 * Makes URL clickable by using url.setMovementMethod(LinkMovementMethod.getInstance())
 */
public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
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
            convertView = inflater.inflate(R.layout.feed_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        Boolean isShipable;
        TextView txtShipable = (TextView) convertView.findViewById(R.id.txt_shipable_status);

        TextView shopName = (TextView) convertView.findViewById(R.id.name);
        TextView productTitle = (TextView) convertView.findViewById(R.id.txt_product_title);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView productDescription = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
       // TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView shopProfilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        FeedImageView productImage = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);

        final FeedItem item = feedItems.get(position);

        shopName.setText(item.getShopName());

        productTitle.setText(item.getProductTitle());
        //isShipable = item.getShipableStatus();

        //if(!isShipable){
          //  txtShipable.setVisibility(convertView.GONE);
       // }

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);



        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getProductDescription())) {
            productDescription.setText(item.getProductDescription());
            productDescription.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            productDescription.setVisibility(View.GONE);
        }

        /* Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }
           */
        // user profile pic
        shopProfilePic.setImageUrl(item.getShopProfilePic(), imageLoader);

        // Feed image
        if (item.getProductImage() != null) {
            productImage.setImageUrl(item.getProductImage(), imageLoader);
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


        Button contactShop = (Button) convertView.findViewById(R.id.btn_feed_details);
        contactShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putString("origin", "home_fragment");
                extras.putInt("product_id", item.getProductId());
                extras.putString("shop_telegram_id", item.getShopTelegramId());
                extras.putString("shop_phone_number", item.getShopPhoneNumber());

                Intent prdouctDetailsIntent = new Intent(activity, ProductDetailsActivity.class);

                prdouctDetailsIntent.putExtras(extras);
                activity.startActivity(prdouctDetailsIntent);
            }
        });

        return convertView;
    }
}
