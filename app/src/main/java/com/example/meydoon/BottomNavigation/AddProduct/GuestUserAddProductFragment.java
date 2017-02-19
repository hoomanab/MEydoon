package com.example.meydoon.BottomNavigation.AddProduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.meydoon.Intro.UserSignUpActivity;
import com.example.meydoon.R;

/**
 * Created by hooma on 2/19/2017.
 */
public class GuestUserAddProductFragment extends Fragment {
    Button signUp;
    TextView abortAddProduct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_product_guest, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** Custom Action Bar*/
        ((AddProductActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        ((AddProductActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AddProductActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_add_product);


        signUp = (Button)view.findViewById(R.id.btn_add_product_sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to signup
                startActivity(new Intent(getActivity(), UserSignUpActivity.class));
            }
        });

        abortAddProduct = (TextView)view.findViewById(R.id.txt_abort_add_product_guest);
        abortAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
