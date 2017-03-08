package com.example.meydoon.BottomNavigation.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.meydoon.BottomNavigation.profile.SettingsActivity;
import com.example.meydoon.R;
import com.example.meydoon.helper.PrefManager;

/**
 * This is the main settings Fragment. This Fragment in called from profile when clicking on Settings icon!
 */
public class SettingsMainFragment extends Fragment {

    private PrefManager pref;
    private LinearLayout logOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new PrefManager(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** Custom actionbar */
        ((SettingsActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((SettingsActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((SettingsActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_settings);


        logOut = (LinearLayout) view.findViewById(R.id.layout_logout_container);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.logoutUser();

            }
        });

    }


}
