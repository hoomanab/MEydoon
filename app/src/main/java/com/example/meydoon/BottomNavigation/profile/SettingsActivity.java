package com.example.meydoon.BottomNavigation.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.meydoon.R;

/**
 * Created by hooma on 3/3/2017.
 */
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_fragment);

        if (findViewById(R.id.settings_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            SettingsMainFragment settingsMainFragment = new SettingsMainFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            settingsMainFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.settings_container, settingsMainFragment).commit();
        }
    }


    /** Handling clicks on actionbar icons */
    public void settingsClickEvent(View view){
        switch (view.getId()){

            /** For product details,
             * @param img_settings_back
             * @param img_edit_profile_back_ **/
            case R.id.img_settings_back:
                finish();
                break;

            case R.id.img_broadcast_message_back:
                getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
