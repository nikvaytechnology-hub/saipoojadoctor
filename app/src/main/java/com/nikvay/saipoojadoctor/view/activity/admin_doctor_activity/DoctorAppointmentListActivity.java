package com.nikvay.saipoojadoctor.view.activity.admin_doctor_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nikvay.saipoojadoctor.R;

public class DoctorAppointmentListActivity extends AppCompatActivity {

    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_list);

        find_All_ID();
        event();
    }

    private void find_All_ID() {
        iv_back=findViewById(R.id.iv_back);
    }

    private void event() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
