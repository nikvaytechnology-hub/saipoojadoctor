package com.nikvay.doctorapplication.view.activity.admin_doctor_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nikvay.doctorapplication.R;

public class AddServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        find_All_IDs();
        events();

    }

    private void find_All_IDs() {
    }

    private void events() {
    }
}
