package com.example.meydoon.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.ViewPagerAdapter;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.helper.PrefManager;
import com.example.meydoon.service.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * This layout appears on the first run of the application.
 * It is built in order to sign-in /sign-up or enter as a guest.
 */
public class IntroFragment extends Fragment implements View.OnClickListener {
    private static String TAG = IntroFragment.class.getSimpleName();

    private ViewPager viewPager;
    private IntroViewPagerAdapter adapter;
    private Button btnRequestSms, btnVerifyOtp;
    private EditText inputName, inputEmail, inputMobile, inputOtp;
    private ProgressBar progressBar;
    private PrefManager pref;
    private ImageButton btnEditMobile;
    private TextView txtEditMobile;
    private LinearLayout layoutEditMobile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        /** Custom Action Bar*/
        ((UserSignUpActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((UserSignUpActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((UserSignUpActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_home);


        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerVertical);
        inputMobile = (EditText) view.findViewById(R.id.inputMobile);
        inputOtp = (EditText) view.findViewById(R.id.inputOtp);
        btnRequestSms = (Button) view.findViewById(R.id.btn_request_sms);
        btnVerifyOtp = (Button) view.findViewById(R.id.btn_verify_otp);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        btnEditMobile = (ImageButton) view.findViewById(R.id.btn_edit_mobile);
        txtEditMobile = (TextView) view.findViewById(R.id.txt_edit_mobile);
        layoutEditMobile = (LinearLayout) view.findViewById(R.id.layout_edit_mobile);

        // view click listeners
        btnEditMobile.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);

        // hiding the edit mobile number
        layoutEditMobile.setVisibility(View.GONE);

        pref = new PrefManager(getActivity());

        // Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.isLoggedIn()) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            getActivity().finish();
        }

        adapter = new IntroViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /**
         * Checking if the device is waiting for sms
         * showing the user OTP screen
         */
        if (pref.isWaitingForSms()) {
            viewPager.setCurrentItem(1);
            layoutEditMobile.setVisibility(View.VISIBLE);
        }


        //btnRequestSms = (Button)view.findViewById(R.id.btn_sign_in);

        /*Clicking on Sign-in button
        btnRequestSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText user_name = (EditText)view.findViewById(R.id.txt_sign_in_mobile_phone);
                final String user_name_string = user_name.getText().toString();

                if (user_name.getText() == null){
                    Toast.makeText(getActivity(), "شماره موبایل نمیتونه خالی باشه!", Toast.LENGTH_LONG).show();
                } else {

                }

                //final EditText password = (EditText)view.findViewById(R.id.txt_password);
                //final String password_string = password.getText().toString();

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
                }


            }
        });*/


        /*Clicking on password recovery link
        TextView password_recovery = (TextView)view.findViewById(R.id.link_password_recovery);
        password_recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 ===================> make Fragment for password recovery <=================
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

*/

        /*Clicking on sing-up link
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

*/
        /*Clicking on guest button
        Button guest_enter = (Button)view.findViewById(R.id.btn_guest_enter);
        guest_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ==============> Fragment for the main page <===============
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

*/

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_request_sms:
                validateForm();
                break;

            case R.id.btn_verify_otp:
                verifyOtp();
                break;

            case R.id.btn_edit_mobile:
                viewPager.setCurrentItem(0);
                layoutEditMobile.setVisibility(View.GONE);
                pref.setIsWaitingForSms(false);
                break;
        }
    }

    /**
     * Validating user details form
     */
    private void validateForm() {
        String mobile = inputMobile.getText().toString().trim();

        // validating empty phone
        if (mobile.length() == 0 ) {
            Toast.makeText(getActivity().getApplicationContext(), "لطفا شماره موبایل رو وارد کنید!", Toast.LENGTH_SHORT).show();
            return;
        }

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile)) {

            // request for sms
            progressBar.setVisibility(View.VISIBLE);

            // saving the mobile number in shared preferences
            pref.setMobileNumber(mobile);

            // requesting for sms
            requestForSMS(mobile);

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "شماره موبایل معتبر نیست!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method initiates the SMS request on the server
     *
     *
     * @param mobile user valid mobile number
     */
    private void requestForSMS(final String mobile) {
        //JSONObject js = new JSONObject();
        JSONObject jsonobject_one = new JSONObject();
        try {


            jsonobject_one.put("user_phone_number", mobile);

            //JSONObject jsonobject = new JSONObject();



            //js.put("data", jsonobject_one.toString());

            //String requestBody = js.toString();

        }catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                Config.URL_REQUEST_SMS, jsonobject_one, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject responseObj) {
                Log.d(TAG, responseObj.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    //boolean error = responseObj.getBoolean("error");
                    String message = responseObj.getString("Message");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    //if (!error) {
                        // boolean flag saying device is waiting for sms
                    pref.setIsWaitingForSms(true);

                        // moving the screen to next pager item i.e otp screen
                    viewPager.setCurrentItem(1);
                    txtEditMobile.setText(pref.getMobileNumber());
                    layoutEditMobile.setVisibility(View.VISIBLE);

                    Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    //} else {
                     //   Toast.makeText(getActivity().getApplicationContext(),
                      //          "Error: " + message,
                      //          Toast.LENGTH_LONG).show();
                    //}

                    // hiding the progress bar
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }) {



            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }




            /**
             * Passing user parameters to our server
             * @return

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);

                Log.e(TAG, "Posting params: " + params.toString());

                return params;
            }*/

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp() {
        String otp = inputOtp.getText().toString().trim();
        String mobile = txtEditMobile.getText().toString().trim();


        if (!otp.isEmpty()) {
            Intent grapprIntent = new Intent(getActivity().getApplicationContext(), HttpService.class);
            Bundle extras = new Bundle();
            extras.putString("otp", otp);
            extras.putString("mobile_number",mobile);
            grapprIntent.putExtras(extras);
            getActivity().startService(grapprIntent);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "لطفا کدی که دریافت کردید رو به صورت کامل وارد کنید!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Regex to validate the mobile number
     * mobile number should be of 11 digits length
     *
     * @param mobile
     * @return
     */
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{11}$";
        return mobile.matches(regEx);
    }



    public class IntroViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.layout_sms;
                    break;
                case 1:
                    resId = R.id.layout_otp;
                    break;
            }
            return viewPager.findViewById(resId);
        }
    }
}
