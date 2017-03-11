package com.example.meydoon.BottomNavigation.AddProduct;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.CustomSpinnerAdapter;
import com.example.meydoon.app.AppController;
import com.example.meydoon.app.Config;
import com.example.meydoon.helper.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hooma on 2/19/2017.
 */
public class ShopRegisterFragment extends Fragment {
    private static String TAG = ShopRegisterFragment.class.getSimpleName();

    private ImageButton imgShopPic;
    private TextView txtShopPic;
    private EditText shopName, shopAddress, shopDescription;
    private Spinner spinnerShopCategory, spinnerCity;
    private Button submitShop, abortSubmition;

    private String shopCategoryName = "";
    private String shopCityName = "";
    private int shopCategoryId = 0;
    private int shopCityId = 0;

    private String defaultTextForSpinner = "انتخاب کنید!";
    private static final String[] shopCategories = {"پوشاک", "خوراک", "ورزشی", "زیور آلات", "آرایشی بهداشتی"};
    private static final String[] cities = {"تهران", "کرمانشاه", "اصفهان", "مشهد", "تبریز", "کرج"};

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int IMPORT_IMAGE_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final String IMAGE_DIRECTORY_NAME = "Meydoon";

    private Uri fileUri, selectedImage; // file url to store image/video

    private PrefManager pref;

    private ProgressBar progressBar;

    private byte[] imageBytes;
    private String encodedimage = "";


    @Override
    public void onStop() {
        super.onStop();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new PrefManager(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** Custom Action Bar*/
        ((AddProductActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        ((AddProductActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AddProductActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_shop_register);

        imgShopPic = (ImageButton)view.findViewById(R.id.shop_sing_up_profile_pic);
        txtShopPic = (TextView)view.findViewById(R.id.shop_txt_set_pic);
        shopName = (EditText)view.findViewById(R.id.shop_txt_set_name);
        shopAddress = (EditText)view.findViewById(R.id.shop_txt_address);
        shopDescription = (EditText)view.findViewById(R.id.shop_description);
        spinnerShopCategory = (Spinner)view.findViewById(R.id.spinner_shop_category);
        spinnerCity = (Spinner)view.findViewById(R.id.spinner_city);
        submitShop = (Button)view.findViewById(R.id.shop_btn_sign_up);
        abortSubmition = (Button)view.findViewById(R.id.shop_btn_abort_sign_up);
        progressBar = (ProgressBar) view.findViewById(R.id.shopRegisterProgressBar);

        /** Importing Image! For now, we just import images! */
        imgShopPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importImage();
            }
        });

        txtShopPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importImage();
            }
        });


        /** Setting Spinners */
        spinnerShopCategory.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, shopCategories, defaultTextForSpinner));
        spinnerCity.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, cities, defaultTextForSpinner));


        spinnerShopCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        shopCategoryName = "cloths";
                        shopCategoryId = 1;
                        break;

                    case 1:
                        shopCategoryName = "food";
                        shopCategoryId = 2;
                        break;

                    case 2:
                        shopCategoryName = "sports";
                        shopCategoryId = 3;
                        break;

                    case 3:
                        shopCategoryName = "jewelry";
                        shopCategoryId = 4;
                        break;

                    case 4:
                        shopCategoryName = "cosmetic";
                        shopCategoryId = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        shopCityName = "Tehran";
                        shopCityId = 1;
                        break;

                    case 1:
                        shopCityName = "Kermanshah";
                        shopCityId = 2;
                        break;

                    case 2:
                        shopCityName = "Isfahan";
                        shopCityId = 3;
                        break;

                    case 3:
                        shopCityName = "Mashhad";
                        shopCityId = 4;
                        break;

                    case 4:
                        shopCityName = "Tabriz";
                        shopCityId = 5;
                        break;

                    case 5:
                        shopCityName = "Karaj";
                        shopCityId = 6;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submitShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Submit shop!
                submitShop();
                // Then go to Add product
            }
        });

        abortSubmition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

    }


    /** Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }



    /**
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /** Importing image from gallery */
    private void importImage(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        //pickPhoto.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);


        startActivityForResult(pickPhoto, IMPORT_IMAGE_REQUEST_CODE);
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);


        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        /*} else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");*/
        } else {
            return null;
        }

        return mediaFile;
    }


    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        switch (requestCode){
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    previewCapturedImage();
                } else if (resultCode == getActivity().RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(getActivity().getApplicationContext(),
                            "لغو عملیات گرفتن تصویر", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // failed to capture image
                    Toast.makeText(getActivity().getApplicationContext(),
                            "عملیات ضبط تصویر ناموفق بود :(", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case IMPORT_IMAGE_REQUEST_CODE:
                if(resultCode == getActivity().RESULT_OK){
                    selectedImage = data.getData();

                    previewImportedImage();
                }
                break;
        }

    }

    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            // hide video preview
            //videoPreview.setVisibility(View.GONE);

            imgShopPic.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;


            Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bitmap = getResizedBitmap(bitmap, 100, 100);
            //InputStream in = new ByteArrayInputStream(bos.toByteArray());

            imgShopPic.setImageBitmap(bitmap);

            imageBytes = bos.toByteArray();
            encodedimage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    private void previewImportedImage (){
        try {
            // hide video preview
            //videoPreview.setVisibility(View.GONE);

            imgShopPic.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bitmap = getResizedBitmap(bitmap, 50, 50);
            //InputStream in = new ByteArrayInputStream(bos.toByteArray());

            imgShopPic.setImageBitmap(bitmap);

            imageBytes = bos.toByteArray();
            encodedimage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

        // CREATE A MATRIX FOR THE MANIPULATION

        Matrix matrix = new Matrix();

        // RESIZE THE BIT MAP

        matrix.postScale(scaleWidth, scaleHeight);

        // RECREATE THE NEW BITMAP

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    public void submitShop(){

        if(shopName.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "برای فروشگاهتون اسمی انتخاب نکردید!", Toast.LENGTH_SHORT).show();


        } else if(shopCategoryName.equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "لطفا حوزه فعالیت فروشگاهتون رو انتخاب کنید!", Toast.LENGTH_SHORT).show();


        } else if(shopCityName.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "لطفا شهری که در اون مستقر هستید رو انتخاب کنید!", Toast.LENGTH_SHORT).show();


        } else {
            JSONObject shopJsonObject = new JSONObject();
            progressBar.setVisibility(View.VISIBLE);
            try {
                shopJsonObject.put("user_id", pref.getUserId());
                shopJsonObject.put("shop_name", shopName.getText().toString());
                shopJsonObject.put("shop_category_name", shopCategoryName);
                shopJsonObject.put("shop_category_id", shopCategoryId);
                shopJsonObject.put("shop_city_name", shopCityName);
                shopJsonObject.put("shop_city_id", shopCityId);
                shopJsonObject.put("shop_address", shopAddress.getText().toString());
                shopJsonObject.put("shop_description", shopDescription.getText().toString());
                shopJsonObject.put("shop_picture", encodedimage);

            }catch (JSONException e){
                e.printStackTrace();
            }

            JsonObjectRequest registerShopJsonObject = new JsonObjectRequest(Request.Method.POST,
                    Config.URL_REGISTER_SHOP, shopJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.d(TAG, jsonObject.toString());
                    try {
                        // Parsing json object response
                        // response will be a json object
                        String error = jsonObject.getString("error");
                        String message = jsonObject.getString("Message");
                        if(error.equals("0")){
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    }catch (JSONException e){
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();

                        progressBar.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            }
            ) {


                @Override
                public String getBodyContentType() {
                    return String.format("application/json; charset=utf-8");
                }
            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

            registerShopJsonObject.setRetryPolicy(policy);
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(registerShopJsonObject);
        }
    }
}
