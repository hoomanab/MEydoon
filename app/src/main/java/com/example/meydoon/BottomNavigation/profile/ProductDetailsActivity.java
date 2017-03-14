package com.example.meydoon.BottomNavigation.profile;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.meydoon.R;

/**
 * Created by hooma on 3/14/2017.
 */
public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.product_details_fragment);

        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        productDetailsFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.add_product_container, productDetailsFragment).commit();

    }

    @Override
    protected void onStop() {
        super.onStop();

        finish();
    }


    /** Handling clicks on actionbar icons */
    public void productDetailsClickEvent(View view){
        switch (view.getId()){

            /** For product details,
             * @param img_product_details_back **/
            case R.id.img_product_details_back:
                finish();
                break;

        }
    }
}
