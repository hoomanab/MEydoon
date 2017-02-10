package com.example.meydoon.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meydoon.R;

/**
 * This layout appears on the first run of the application.
 * It is built in order to sign-in /sign-up or enter as a guest.
 */
public class IntroFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button sign_in = (Button)view.findViewById(R.id.btn_sign_in);

        //Clicking on Sign-in button
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText user_name = (EditText)view.findViewById(R.id.txt_sign_in_mobile_phone);
                final String user_name_string = user_name.getText().toString();

                final EditText password = (EditText)view.findViewById(R.id.txt_password);
                final String password_string = password.getText().toString();

               /* if(user_name_string.equals("09197248440") && password_string.equals("salam")){

                    //  ===================> make main page a Fragment <======================



                    VerifyMobileFragment newFragment = new VerifyMobileFragment();
                    Bundle args = new Bundle();
                    newFragment.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.user_sign_up_container, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();

                }else {
                    if(!(user_name_string.equals("09197248440"))) {
                        Toast.makeText(LoginActivity.this, "نام کاربری اشتباه است!", Toast.LENGTH_LONG)
                                .show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "کلمه عبور اشتباه است!", Toast.LENGTH_LONG)
                                .show();
                    }
                }*/


            }
        });


        //Clicking on password recovery link
        TextView password_recovery = (TextView)view.findViewById(R.id.link_password_recovery);
        password_recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* ===================> make Fragment for password recovery <=================
                VerifyMobileFragment newFragment = new VerifyMobileFragment();
                Bundle args = new Bundle();
                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.user_sign_up_container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            */}
        });



        //Clicking on sing-up link
        TextView sign_up = (TextView)view.findViewById(R.id.user_link_to_sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SignUpMobileFragment gotToSignUp = new SignUpMobileFragment();
                Bundle args = new Bundle();
                gotToSignUp.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.user_sign_up_container, gotToSignUp);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });


        //Clicking on guest button
        Button guest_enter = (Button)view.findViewById(R.id.btn_guest_enter);
        guest_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ==============> Fragment for the main page <===============
            }
        });



    }
}
