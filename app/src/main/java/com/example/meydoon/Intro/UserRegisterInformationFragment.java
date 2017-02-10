package com.example.meydoon.Intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meydoon.R;

/**
 * Created by hooma on 2/6/2017.
 */
public class UserRegisterInformationFragment extends Fragment {
    private Button submit, cancel;
    private TextView linkToUploadProfilePic;
    private EditText name, password;
    ImageView btnToUpLoadProfilePic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit = (Button)view.findViewById(R.id.btn_sign_up);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ==========> Submit the information and go to main page <===========
                linkToUploadProfilePic = (TextView)view.findViewById(R.id.txt_set_profile_pic);
                btnToUpLoadProfilePic = (ImageView)view.findViewById(R.id.user_sing_up_profile_pic);
                // select the profile pic and upload it

            }
        });

        cancel = (Button)view.findViewById(R.id.btn_abort_sign_up);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ==================> Go to the main page <====================
            }
        });

    }
}
