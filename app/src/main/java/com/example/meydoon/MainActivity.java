package com.example.meydoon;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.meydoon.BottomNavigation.AddProductFragment;
import com.example.meydoon.BottomNavigation.HomeFragment;
import com.example.meydoon.BottomNavigation.NotificationsInboxFragment;
import com.example.meydoon.BottomNavigation.ProfileFragment;
import com.example.meydoon.BottomNavigation.SearchFragment;
import com.example.meydoon.Intro.IntroFragment;
import com.example.meydoon.Intro.UserSignUpActivity;
import com.example.meydoon.mainTabs.BottomNavigationTabFragment;
import com.example.meydoon.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView bottomNavigationView;

    private int mSelectedItem;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // =====================> Products and shops tabs will not be in the MVP
    private int[] tabIcons = {
           // R.drawable.ic_product_filter_tab,
            R.drawable.ic_meydoon_tab,
      //      R.drawable.ic_shop_tab
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ********** Here is for the tabs! in version 2.0 we will implement other parts **********
        /*viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();*/

        /** Custom Action Bar */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.home_actionbar);

        View view =getSupportActionBar().getCustomView();
        ImageButton imageButton= (ImageButton)view.findViewById(R.id.meydoon_tab);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** We should make it act like a tab
                 *  Note: After adding other icons to the action bar, by clicking on them, main_container must change!*/

            }
        });

        /** Bottom navigation menu configurations*/
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavSelectedItem(item);
                return true;
            }
        });

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        /** Initialize Home Fragment */
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.main_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            HomeFragment goToHome = new HomeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            goToHome.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, goToHome).commit();
        }

    }

    public void bottomNavSelectedItem(MenuItem item){
        switch (item.getItemId()) {
            case R.id.btm_nav_profile:
                ProfileFragment goToProfile = new ProfileFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                goToProfile.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.navigation_container, goToProfile).commit();
                break;

            case R.id.btm_nav_notifications_inbox:
                NotificationsInboxFragment goToNotificationInbox = new NotificationsInboxFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                goToNotificationInbox.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.navigation_container, goToNotificationInbox).commit();
                break;

            case R.id.btm_nav_add_item:
                AddProductFragment goToAddProduct = new AddProductFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                goToAddProduct.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.navigation_container, goToAddProduct).commit();
                break;

            case R.id.btm_nav_search:
                SearchFragment gotToSearch = new SearchFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                gotToSearch.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.navigation_container, gotToSearch).commit();
                break;

            case R.id.btm_nav_home:
                // Create a new Fragment to be placed in the activity layout
                HomeFragment goToHome = new HomeFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                goToHome.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, goToHome).commit();
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(new ProductsFilterFragment(), "فیلتر محصولات");
        adapter.addFragment(new BottomNavigationTabFragment(), "میدون");
        //adapter.addFragment(new ShopsCategoryFragment(), "فیلتر فروشگاه ها");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        //tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        //tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    /** ********** If Meydoon is run for the first time, it goes to intro.xml layout ***********/
    @Override
    protected void onResume() {
        super.onResume();
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun) {
            //show start activity
            startActivity(new Intent(MainActivity.this, UserSignUpActivity.class));
            Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
