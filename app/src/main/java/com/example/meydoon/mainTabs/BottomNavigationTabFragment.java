package com.example.meydoon.mainTabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.meydoon.BottomNavigation.HomeFragment;
import com.example.meydoon.BottomNavigationViewHelper;
import com.example.meydoon.R;

/**
 * Created by hooma on 2/6/2017.
 */
public class BottomNavigationTabFragment extends Fragment {
    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView bottomNavigationView;

    private int mSelectedItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_navigation, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Bottom navigation menu configurations
        bottomNavigationView = (BottomNavigationView)view.
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavSelectedItem(item);
                return true;
            }
        });

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
    }

    public void bottomNavSelectedItem(MenuItem item){
        switch (item.getItemId()) {
            case R.id.btm_nav_profile:
                // =========================> Do sth like MenuFragment <=========================
                break;
            case R.id.btm_nav_notifications_inbox:

                break;
            case R.id.btm_nav_add_item:

                break;
            case R.id.btm_nav_search:

                break;
            case R.id.btm_nav_home:

                HomeFragment goToHome = new HomeFragment();
                Bundle args = new Bundle();
                goToHome.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.navigation_container, goToHome);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                break;
        }
    }

}
