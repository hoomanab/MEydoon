package com.example.meydoon.BottomNavigation.AddProduct;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.meydoon.R;

/**
 * Created by hooma on 2/19/2017.
 */
public class NormalUserAddProductFragment extends Fragment {
    Button submitShop;
    TextView backAndAbort;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_product_normal_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submitShop = (Button)view.findViewById(R.id.btn_submit_shop);
        submitShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopRegisterFragment shopRegisterFragment = new ShopRegisterFragment();
                Bundle args = new Bundle();
                shopRegisterFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.add_product_container, shopRegisterFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        backAndAbort = (TextView)view.findViewById(R.id.txt_abort_add_normal_user);
        backAndAbort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
