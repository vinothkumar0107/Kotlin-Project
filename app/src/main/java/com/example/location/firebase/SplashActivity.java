package com.example.location.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.location.R;

public class SplashActivity extends AppCompatActivity
{
    private static final int SPLASH_SCREEN_TIME_OUT = 2000;
    String email,password;
    SharedPreferences sharedPreferences;
    Boolean isFirstTime;
    MySharedPerference mySharedPerference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


       mySharedPerference = new MySharedPerference(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              /*  sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                isFirstTime = sharedPreferences.getBoolean("isFristTime",true);
                if(isFirstTime){
                    editor.putBoolean("",false);
                    editor.commit();
                }else{
                    Intent intent = new Intent(SplashActivity.this, BottomActivity.class);
                    startActivity(intent);
                }*/

//                SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
//
//                 isLoggedIn = prefs.getBoolean("isLoggedIn", true);

                if (mySharedPerference.getBoolean(MySharedPerference.login))
                {
                    Intent intent = new Intent(SplashActivity.this,BottomActivity.class);
                    startActivity(intent);
                    finish();
                    // Don't display login activity
                } else {
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    // Display Login Activity
                }
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }
}