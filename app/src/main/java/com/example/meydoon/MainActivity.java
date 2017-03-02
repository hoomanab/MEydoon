package com.example.meydoon;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.meydoon.BottomNavigation.AddProduct.AddProductActivity;
import com.example.meydoon.BottomNavigation.HomeFragment;
import com.example.meydoon.BottomNavigation.NotificationsInboxFragment;
import com.example.meydoon.BottomNavigation.profile.ProfileFragment;
import com.example.meydoon.BottomNavigation.SearchFragment;
import com.example.meydoon.BottomNavigation.profile.ProfileNotificationsActivity;
import com.example.meydoon.BottomNavigation.profile.ProfileNotificationsFragment;
import com.example.meydoon.Intro.ProceedActivity;
import com.example.meydoon.Intro.ProceedFragment;
import com.example.meydoon.Intro.UserSignUpActivity;
import com.example.meydoon.adapter.ViewPagerAdapter;
import com.example.meydoon.helper.PrefManager;
import com.example.meydoon.mainTabs.BottomNavigationTabFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView bottomNavigationView;

    private int mSelectedItem;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PrefManager pref;
    private HashMap<String, String> user;
    private int user_id;
    private String user_name;
    private String user_phone_number;

    private Bundle extras;
    private Boolean loginStatus;


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
        getSupportActionBar().setCustomView(R.layout.actionbar_home);


        /** Setting sessions **/
        pref = new PrefManager(getApplicationContext());

        user_id = pref.getUserId();
        user_phone_number = pref.getMobileNumber();
        user_name = pref.getUserName();


        View view = getSupportActionBar().getCustomView();
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

        /** Making Home the initial selected item */
        bottomNavigationView.getMenu().getItem(4).setChecked(true);
        bottomNavSelectedItem(bottomNavigationView.getMenu().getItem(4));



    }


    /** Bottom navigation selection */
    public void bottomNavSelectedItem(MenuItem item){
        switch (item.getItemId()) {
            case R.id.btm_nav_profile:
                loginStatus = pref.isLoggedIn();

                /** If the is logged in, he can proceed! */
                //if(loginStatus){
                    ProfileFragment goToProfile = new ProfileFragment();
                //    putExtrasForFragment();
                //    goToProfile.setArguments(extras);

                    getSupportFragmentManager().beginTransaction().add(R.id.main_container, goToProfile).commit();
              //  }else {
                    /** If the user is a guest, he will be redirected to loggin fragment */
                  //  startActivity(new Intent(this, ProceedActivity.class));
              //  }
                break;

            case R.id.btm_nav_notifications_inbox:
                loginStatus = pref.isLoggedIn();

                /** If the is logged in, he can proceed! */
                if(loginStatus){
                    NotificationsInboxFragment goToNotification = new NotificationsInboxFragment();
                    putExtrasForFragment();
                    goToNotification.setArguments(extras);

                    getSupportFragmentManager().beginTransaction().add(R.id.main_container, goToNotification).commit();
                }else {
                    /** If the user is a guest, he will be redirected to loggin fragment */
                    startActivity(new Intent(this, ProceedActivity.class));
                }
                break;

            case R.id.btm_nav_add_item:
                //Intent goToAddProduct = new Intent(getBaseContext(), AddProductActivity.class);
                //goToAddProduct.putExtra("Previously Selected Menu Item", bottomNavigationView.getMenu().getItem(4).toString());
                loginStatus = pref.isLoggedIn();

                /** If the is logged in, he can proceed! */
                if(loginStatus){

                    /** ====================> Check if the user has shop! <====================*/

                    Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                    putExtrasForFragment();
                    intent.putExtras(extras);
                    startActivity(intent);
                }else {
                    /** If the user is a guest, he will be redirected to loggin fragment */
                    startActivity(new Intent(this, ProceedActivity.class));
                }


                /** ===============> On back-press menu item selected previously not implemented! */
                break;

            case R.id.btm_nav_search:
                SearchFragment gotToSearch = new SearchFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                //gotToSearch.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, gotToSearch).commit();
                break;

            case R.id.btm_nav_home:

                /** In Home, we should check if the user is guest or not to show products accordingly! */
                // Create a new Fragment to be placed in the activity layout
                HomeFragment goToHome = new HomeFragment();
                putExtrasForFragment();
                goToHome.setArguments(extras);
                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                //goToHome.setArguments(getIntent().getExtras());

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


    private void putExtrasForFragment(){
        extras = new Bundle();
        extras.putInt("user_id", user_id);
        extras.putString("user_name", user_name);
        extras.putString("user_phone_number", user_phone_number);
    }



    /** Handling clicks on actionbar icons */
    public void mainActivityClickEvent(View view){
        switch (view.getId()){

            /** For shop profile actionbar,
             * @param img_setting
             * @param img_send_notification**/
            case R.id.img_settings:
                Toast.makeText(this, "تنظیمات", Toast.LENGTH_LONG).show();
                break;

            case R.id.img_send_notification:
                ProfileNotificationsFragment goToProfileNotifications = new ProfileNotificationsFragment();
                goToProfileNotifications.setArguments(getIntent().getExtras());


                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.add(R.id.main_container, goToProfileNotifications);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                //startActivity(new Intent(MainActivity.this, ProfileNotificationsActivity.class));
                break;


            /** For product details,
             * @param img_back **/
            case R.id.img_back:
                this.getSupportFragmentManager().popBackStackImmediate();
                break;

            /** For Profile notifications back icon,
             * @param img_profile_notification_back **/
            case R.id.img_profile_notification_back:
                getSupportFragmentManager().popBackStackImmediate();
                break;

            case R.id.img_broadcast_message_back:
                getSupportFragmentManager().popBackStackImmediate();
        }
    }

}
