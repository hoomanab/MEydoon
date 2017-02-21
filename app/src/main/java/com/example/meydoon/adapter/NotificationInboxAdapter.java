package com.example.meydoon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.data.NotificationInboxItem;
import com.example.meydoon.data.SearchItem;

import java.util.List;

/**
 * Created by hooma on 2/20/2017.
 */
public class NotificationInboxAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NotificationInboxItem> notificationInboxItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public NotificationInboxAdapter(Activity activity, List<NotificationInboxItem> notificationInboxItems) {
        this.activity = activity;
        this.notificationInboxItems = notificationInboxItems;
    }


    @Override
    public int getCount() {
        return notificationInboxItems.size();
    }

    @Override
    public Object getItem(int location) {
        return notificationInboxItems.get(location);
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
            convertView = inflater.inflate(R.layout.notification_inbox_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView shopName = (TextView) convertView.findViewById(R.id.notification_shop_name);
        TextView timeStamp = (TextView) convertView
                .findViewById(R.id.notification_inbox_timestamp);
        TextView notificationMessage = (TextView) convertView
                .findViewById(R.id.notification_title);
        // TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView ShopProfilePic = (NetworkImageView) convertView
                .findViewById(R.id.notification_inbox_shop_profile_pic);

        NotificationInboxItem item = notificationInboxItems.get(position);

        shopName.setText(item.getShopName());

        timeStamp.setText(item.getNotificationTime());

        notificationMessage.setText(item.getNotificationMessageTitle());

        // user profile pic
        ShopProfilePic.setImageUrl(item.getShopProfilePic(), imageLoader);

        LinearLayout shopUrl = (LinearLayout) convertView.findViewById(R.id.notification_item_container);
        shopUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** go to the Complete message! */
            }
        });





        return convertView;
    }
}
