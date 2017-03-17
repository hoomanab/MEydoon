package com.example.meydoon.BottomNavigation.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.meydoon.R;

/**
 * Created by hooma on 3/17/2017.
 */
public class ContactShopFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = ContactShopFragment.class.getSimpleName();

    private LinearLayout contactTelegram, contactSms, contactCall;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_shop, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((ProductDetailsActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_contact_shop);

        contactTelegram = (LinearLayout) view.findViewById(R.id.contact_shop_telegram);
        contactSms = (LinearLayout) view.findViewById(R.id.contact_shop_sms);
        contactCall = (LinearLayout) view.findViewById(R.id.contact_shop_call);

        onClick(view);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.contact_shop_telegram:

                break;

            case R.id.contact_shop_sms:

                break;

            case R.id.contact_shop_call:

                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().finish();
    }
}
