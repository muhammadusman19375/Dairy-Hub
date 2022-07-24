package com.example.fyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.Utils;

public class SplashActivity extends AppCompatActivity {
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        //code for action bar
//        getSupportActionBar().hide();

        //function for delay in splash activity
        delay();

        utils=new Utils(SplashActivity.this);
    }

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                utils.getToken();
//                if(utils.isLoggedIn()){
                    Intent intent=new Intent(SplashActivity.this,PhoneNumberActivity.class);
                    startActivity(intent);
                    finish();
//                }
//                else{
//                    Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        },1000);
    }
}