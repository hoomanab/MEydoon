package com.example.meydoon.BottomNavigation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.meydoon.R;

/**
 * Created by hooma on 3/1/2017.
 */
public class BroadcastMessageOutboxActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_notification_fragment);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.profile_notification_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            BroadcastMessageOutboxFragment goToProfileNotifications = new BroadcastMessageOutboxFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            goToProfileNotifications.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.profile_notification_container, goToProfileNotifications).commit();
        }
    }


    /** Handling clicks on actionbar icons */
    public void broadcastMessageOutboxClickEvent(View view){
        switch (view.getId()){

            /** For product details,
             * @param img_profile_notification_back **/
            case R.id.img_profile_notification_back:
                finish();
                break;

            case R.id.img_broadcast_message_back:
                getSupportFragmentManager().popBackStackImmediate();
        }
    }

}
