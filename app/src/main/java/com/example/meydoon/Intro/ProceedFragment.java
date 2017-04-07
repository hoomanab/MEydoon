package com.example.meydoon.Intro;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.meydoon.BottomNavigation.HomeFragment;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;

/**
 * Created by hooma on 3/1/2017.
 */
public class ProceedFragment extends Fragment {
    private Button proceed;
    private TextView abort;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guest_proceed, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** Custom Action Bar*/
        ((ProceedActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((ProceedActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((ProceedActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_home);

        proceed = (Button) view.findViewById(R.id.btn_proceed);
        abort = (TextView) view.findViewById(R.id.proceed_abort);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserSignUpActivity.class);
                startActivity(intent);
            }
        });

        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().finish();
    }
}
