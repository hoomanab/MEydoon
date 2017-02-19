package com.example.meydoon.BottomNavigation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.meydoon.R;


/**
 * Created by hooma on 2/19/2017.
 */
public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_fragment);

        /** Check if user is a shop_user or is signed-in */


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.add_product_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            AddProductFragment goToAddProduct = new AddProductFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            goToAddProduct.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.add_product_container, goToAddProduct).commit();
        }
    }

    public void setActionBarTitle(String title){
        getActionBar().setTitle(title);
    }

}
