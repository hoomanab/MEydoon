package com.example.meydoon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hooma on 1/31/2017.
 */
public class Splash extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(SPLASH_DISPLAY_LENGTH);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    finish();
                    Intent openStartingPoint = new Intent(Splash.this, MainActivity.class);
                    startActivity(openStartingPoint);

                }
            }
        };
        timer.start();



        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 // Create an Intent that will start the Menu-Activity.
                Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);*/
    }
}
