package com.nikvay.saipoojadoctor.view.activity.doctor_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nikvay.saipoojadoctor.R;

public class ClassAppointmentListActivity extends AppCompatActivity {

    private ImageView iv_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_appointment_list);

        find_All_Ids();
        events();
    }

    private void find_All_Ids() {
        iv_close=findViewById(R.id.iv_close);
    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
