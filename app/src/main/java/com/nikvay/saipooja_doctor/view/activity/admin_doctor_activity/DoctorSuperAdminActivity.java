package com.nikvay.saipooja_doctor.view.activity.admin_doctor_activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.BaseApi;
import com.nikvay.saipooja_doctor.utils.ShowProgress;

public class DoctorSuperAdminActivity extends AppCompatActivity {


    private WebView webViewAdminPanel;
    private ShowProgress showProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_super_admin);

        String admin_url= BaseApi.BASE_URL;
        webViewAdminPanel=findViewById(R.id.webViewAdminPanel);


        showProgress=new ShowProgress(DoctorSuperAdminActivity.this);



        webViewAdminPanel.loadUrl(admin_url);
        webViewAdminPanel.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                showProgress.showDialog();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showProgress.dismissDialog();
            }

        });

        webViewAdminPanel.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "back press",
                    Toast.LENGTH_LONG).show();

        return true;
        // Disable back button..............
    }
}
