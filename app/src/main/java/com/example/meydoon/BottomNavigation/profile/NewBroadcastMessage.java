package com.example.meydoon.BottomNavigation.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.meydoon.R;

/**
 * Created by hooma on 3/2/2017.
 */
public class NewBroadcastMessage extends Fragment {
    EditText txtBroadcastMessageTitle, txtBroadcastMessage;
    Button btnBroadcastMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.broadcast_message, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /** Custom Actionbar */
        ((BroadcastMessageOutboxActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((BroadcastMessageOutboxActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((BroadcastMessageOutboxActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_broadcast_message);


        txtBroadcastMessageTitle = (EditText) view.findViewById(R.id.txt_broadcast_message_title);
        txtBroadcastMessage = (EditText) view.findViewById(R.id.txt_broadcast_message);
        btnBroadcastMessage = (Button) view.findViewById(R.id.btn_broadcast);

        btnBroadcastMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**  Send JSONObject to the server.
                 *   In server, the message should be sent to the followers! */
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /** Custom Action Bar for previous Fragment*/
        ((BroadcastMessageOutboxActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((BroadcastMessageOutboxActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((BroadcastMessageOutboxActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_profile_notification);
    }
}
