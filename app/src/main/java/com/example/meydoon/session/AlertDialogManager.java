package com.example.meydoon.session;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.meydoon.R;

/**
 * Function to display simple Alert Dialog
 * //@param context - application context
 * //@param title - alert dialog title
 * //@param message - alert message
 * //@param status - success/failure (used to set icon)
 *               - pass null if you don't want icon
 * */
public class AlertDialogManager {
   /* public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? android.R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }*/
}
