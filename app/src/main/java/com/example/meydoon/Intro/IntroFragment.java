package com.example.meydoon.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.helper.PrefManager;
import com.example.meydoon.service.VerifyOtpHttpService;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Button btnRequestSms, btnVerifyOtp, btnGuestEnter;
    private EditText inputMobile, inputOtp;
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

        btnGuestEnter = (Button) view.findViewById(R.id.btn_guest_enter);

        // view click listeners
        btnEditMobile.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);

        // hiding the edit mobile number
        layoutEditMobile.setVisibility(View.GONE);

        pref = new PrefManager(getActivity());

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


        btnGuestEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

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



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Config.URL_REQUEST_SMS, jsonobject_one, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject responseObj) {
                Log.d(TAG, responseObj.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String error = responseObj.getString("error");
                    String message = responseObj.getString("Message");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    if (error.equals("0")) {
                        // boolean flag saying device is waiting for sms
                        pref.setIsWaitingForSms(true);

                        // moving the screen to next pager item i.e otp screen
                        viewPager.setCurrentItem(1);
                        txtEditMobile.setText(pref.getMobileNumber());
                        layoutEditMobile.setVisibility(View.VISIBLE);

                        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Error: " + message,
                               Toast.LENGTH_LONG).show();
                    }

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


            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_phone_number", mobile);

                Log.e(TAG, "Posting params: " + params.toString());

                return params;
            }*/

        };

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp() {
        String otp = inputOtp.getText().toString();
        String mobile = txtEditMobile.getText().toString();


        if (!otp.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            Intent grapprIntent = new Intent(getActivity(), VerifyOtpHttpService.class);
            Bundle extras = new Bundle();
            extras.putString("otp", otp);
            extras.putString("user_phone_number",mobile);
            grapprIntent.putExtras(extras);
            getActivity().startService(grapprIntent);
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
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

    @Override
    public void onStop() {
        super.onStop();
        getActivity().finish();
    }
}
