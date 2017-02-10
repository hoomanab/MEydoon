package com.example.meydoon.Intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
 * Created by hooma on 2/5/2017.
 */
public class SignUpMobileFragment extends Fragment {

    private Button btn_mobile_verification;
    private EditText mobile_phone;
    private TextView back_to_intro;


    public interface OnClickListener{
        public void onVerifySelected();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_mobile_phone, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btn_mobile_verification = (Button)view.findViewById(R.id.btn_go_to_verification);
        mobile_phone = (EditText)view.findViewById(R.id.txt_get_phone);




        btn_mobile_verification.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /* =====================> Send an SMS here <=====================
                * Create a 6 letter word!
                * This code should match the code user enters on VerifyMobileFragment
                */
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

            }
        });


        // ******** Back Link to the Intro layout ********
        back_to_intro = (TextView)view.findViewById(R.id.txt_back_to_intro);
        back_to_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntroFragment backToIntro = new IntroFragment();
                Bundle args = new Bundle();
                backToIntro.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.user_sign_up_container, backToIntro);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });
    }




}
