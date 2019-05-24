package com.nikvay.doctorapplication.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import io.fabric.sdk.android.Fabric;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity {

    String is_login;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        is_login = SharedUtils.getSharedUtils(getApplicationContext());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        } else {
            handlerCall();
        }

    }

    private void checkPermissions() {
        RxPermissions.getInstance(SplashActivity.this)
                .request(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        initialize(aBoolean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    private void initialize(Boolean isAppInitialized) {
        if (isAppInitialized) {
            handlerCall();

        } else {
            /* If one Of above permission not grant show alert (force to grant permission)*/
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("Alert");
            builder.setMessage("All permissions necessary");

            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkPermissions();
                }
            });

            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }
    }

    private void handlerCall() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (is_login.equalsIgnoreCase("") || is_login.equalsIgnoreCase("false")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);
    }
}
