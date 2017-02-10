package com.example.meydoon.BottomNavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.FeedListAdapter;
import com.example.meydoon.data.FeedItem;

import java.util.List;

/**
 * Created by hooma on 2/8/2017.
 */
public class SearchFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /** Custom Action Bar*/
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.search_actionbar);


        EditText search = (EditText)view.findViewById(R.id.inputSearch);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /******************************************performSearch();**************************/
                    return true;
                }
                return false;
            }
        });

    }
}
