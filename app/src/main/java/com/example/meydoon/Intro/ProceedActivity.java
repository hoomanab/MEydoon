package com.example.meydoon.Intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.receiver.ConnectivityReceiver;

/**
 * Created by hooma on 3/1/2017.
 */
public class ProceedActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private static String TAG = ProceedActivity.class.getSimpleName();

    private TextView txtNoConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guest);

        txtNoConnection = (TextView) findViewById(R.id.proceed_txt_no_internet);
        checkConnection();

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.proceed_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ProceedFragment goToProceed = new ProceedFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            goToProceed.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.proceed_container, goToProceed).commit();
        }
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showNoConnection(isConnected);
    }

    private void showNoConnection(boolean isConnected){
        if(isConnected) {
            txtNoConnection.setVisibility(View.GONE);
        } else{
            txtNoConnection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v(TAG, "MainActivity resumed!");

        checkConnection();
        AppController.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showNoConnection(isConnected);
    }

}
