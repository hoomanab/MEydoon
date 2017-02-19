package com.example.meydoon.BottomNavigation;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.meydoon.MainActivity;
import com.example.meydoon.R;
import com.example.meydoon.adapter.CustomSpinnerAdapter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *  This is the page where a shop user can add a product!
 */
public class AddProductFragment extends Fragment {
    private ImageButton addProductImageCapture, addProductImageImport;
    private EditText productName, productPrice, productDescription;
    private Spinner spinnerProductCategory;
    private ImageView imgPreview;
    private Button submitProduct, abort;

    private String productCategoryName;
    private int productCategoryId;

    //List<ProductCategoryItem> productCategoryItems;
    //ProductCategoryItem cloths, food, sports, jewelry, cosmetic;


    /** Service Values must be assinged to the string! */
    private static final String[] productCategories = {"پوشاک", "خوراک", "ورزشی", "زیور آلات", "آرایشی بهداشتی"};
    private String defaultTextForSpinner = "انتخاب کنید!";
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Meydoon";

    private Uri fileUri, selectedImage; // file url to store image/video

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int IMPORT_IMAGE_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Moving the layout up to soft keyboard! */
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_product, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** Custom Action Bar*/
        ((AddProductActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        ((AddProductActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AddProductActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_add_product);

        addProductImageCapture = (ImageButton)view.findViewById(R.id.img_add_product_capture);
        addProductImageImport = (ImageButton)view.findViewById(R.id.img_add_product_import);
        productName = (EditText)view.findViewById(R.id.txt_product_name);
        spinnerProductCategory = (Spinner)view.findViewById(R.id.spinner_product_category);
        productPrice = (EditText)view.findViewById(R.id.product_price);
        productDescription = (EditText)view.findViewById(R.id.product_description);
        imgPreview = (ImageView)view.findViewById(R.id.img_preview);
        submitProduct = (Button)view.findViewById(R.id.btn_add_product);
        abort = (Button)view.findViewById(R.id.btn_abort_add_product);

        /** Getting Image **/
        addProductImageCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDeviceSupportCamera()){
                    captureImage();
                }
                else
                    Toast.makeText(getActivity(), "دوربین دستگاه شما درست کار نمیکنه!", Toast.LENGTH_LONG).show();

            }
        });

        addProductImageImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importImage();
            }
        });

        /** Setting Adapter for product category Spinner */
        /*    ===> This will be for future <===

        cloths = new ProductCategoryItem();
        cloths.setProductCategoryId(1);
        cloths.setProductCategoryName("پوشاک");

        food = new ProductCategoryItem();
        food.setProductCategoryId(2);
        food.setProductCategoryName("خوراکی");

        sports = new ProductCategoryItem();
        sports.setProductCategoryId(3);
        sports.setProductCategoryName("ورزشی");

        jewelry = new ProductCategoryItem();
        jewelry.setProductCategoryId(4);
        jewelry.setProductCategoryName("زیور آلات");

        cosmetic = new ProductCategoryItem();
        cosmetic.setProductCategoryId(1);
        cosmetic.setProductCategoryName("آرایشی بهداشتی");

        productCategoryItems = new ArrayList<ProductCategoryItem>();
        productCategoryItems.add(1, cloths);
        productCategoryItems.add(2, food);
        productCategoryItems.add(3, sports);
        productCategoryItems.add(4,jewelry);
        productCategoryItems.add(5, cosmetic);*/

        //ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, productCategories);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerProductCategory.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_row, productCategories, defaultTextForSpinner));

        spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        productCategoryName = "cloths";
                        productCategoryId = 1;
                        break;

                    case 1:
                        productCategoryName = "food";
                        productCategoryId = 2;
                        break;

                    case 2:
                        productCategoryName = "sports";
                        productCategoryId = 3;
                        break;

                    case 3:
                        productCategoryName = "jewelry";
                        productCategoryId = 4;
                        break;

                    case 4:
                        productCategoryName = "cosmetic";
                        productCategoryId = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /** Add Product Button */
        submitProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Upload product
            }
        });


        /** Abort adding product button */
        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
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

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;


            Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bitmap = getResizedBitmap(bitmap, 360, 360);
            //InputStream in = new ByteArrayInputStream(bos.toByteArray());

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    private void previewImportedImage (){
        try {
            // hide video preview
            //videoPreview.setVisibility(View.GONE);

            imgPreview.setVisibility(View.VISIBLE);

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
            bitmap = getResizedBitmap(bitmap, 360, 360);
            //InputStream in = new ByteArrayInputStream(bos.toByteArray());

            imgPreview.setImageBitmap(bitmap);
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


}
