package com.sarangshende.themoviedb;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sarangshende.themoviedb.NetworkCheck.CheckNetwork;


public class SplashActivity extends AppCompatActivity
{

    Context ctx;
    ViewGroup transitionsContainer;
    ImageView iv;
    private Handler progressBarHandler = new Handler();

    View mLoadingView;
    TextView tv_internet_;
    Context context;




    //===============================================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ctx = this;
        tv_internet_ = findViewById(R.id.tv_internet);


        context = SplashActivity.this;


        transitionsContainer = findViewById(R.id.transitions_container);
        iv = transitionsContainer.findViewById(R.id.iv_splash);
        mLoadingView = findViewById(R.id.pb);

        progress();
    }

    //===================================================================================
    public void progress()
    {

        new Thread(new Runnable() {
            public void run() {

                while (!CheckNetwork.isInternetAvailable(getApplicationContext()))
                {
                    tv_internet_.setVisibility(View.VISIBLE);
                    // performing operation

                    try {
                        Thread.sleep(100);


                    } catch (InterruptedException e) {
                        Log.e("InterruptedException", "== " + e);

                    }
                    // Updating the progress bar
                    progressBarHandler.post(new Runnable() {
                        public void run()
                        {

                        }
                    });
                }
                // performing operation if file is downloaded,
                if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
                    try {
                        Thread.sleep(2000);

                        Intent intent  = new Intent(context,MainActivity.class);
                        startActivity(intent);
                        finish();


                        } catch(InterruptedException e)
                        {
                            e.printStackTrace();
                        }



                }

            }
        }).start();
    }






}
