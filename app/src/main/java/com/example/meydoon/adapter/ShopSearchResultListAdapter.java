package com.example.meydoon.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.meydoon.FeedImageView;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.data.SearchItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hooma on 2/10/2017.
 */
public class ShopSearchResultListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<SearchItem> searchItems;
    private List<SearchItem> filteredData;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ItemFilter mFilter = new ItemFilter();

    public ShopSearchResultListAdapter(Activity activity, List<SearchItem> searchItems) {
        this.activity = activity;
        this.searchItems = searchItems;
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

        // user profile pic
        ShopProfilePic.setImageUrl(item.getShopProfilePic(), imageLoader);

        LinearLayout shopUrl = (LinearLayout) convertView.findViewById(R.id.shop_search_result_container);
        shopUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** go to shop's profile with item.getShopUrl() */
            }
        });





        return convertView;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<SearchItem> list = searchItems;

            int count = list.size();
            final List<SearchItem> nlist = new ArrayList<SearchItem>(count);

            SearchItem item;

            for (int i = 0; i < count; i++) {
                item = list.get(i);
                if (item.getShopName().toLowerCase().contains(filterString)) {
                    nlist.add(item);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            searchItems.clear();
            searchItems = nlist;

            return results;
        }


        @SuppressWarnings("unchecked")
        @Override
        public void publishResults(CharSequence constraint, FilterResults results) {
            searchItems = (ArrayList<SearchItem>) results.values;
            notifyDataSetChanged();
        }

    }





    /*


    public ArrayList<SearchItem> getFilter(CharSequence charSequence){
        ArrayList<SearchItem> filteredItems = new ArrayList<SearchItem>();
        if(charSequence.toString().length() > 0){
            String normalCS = charSequence.toString().toLowerCase();
            int i;
            for(i = 0; i < searchItems.size(); i++){
                if(searchItems.get(i).getShopName().toLowerCase().contains(normalCS)){
                    filteredItems.add(searchItems.get(i));
                }
            }
        }

        return filteredItems;
    }


     */




}
