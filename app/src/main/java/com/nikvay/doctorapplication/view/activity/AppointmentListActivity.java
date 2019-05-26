package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.utils.StaticContent;

public class AppointmentListActivity extends AppCompatActivity {


    private String status,appointmentName;
    private ImageView iv_close;
    private TextView textAppointmentTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        find_All_IDs();
        events();
    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void find_All_IDs() {
        iv_close=findViewById(R.id.iv_close);
        textAppointmentTitleName=findViewById(R.id.textAppointmentTitleName);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            status=bundle.getString(StaticContent.IntentKey.STATUS);
            appointmentName=bundle.getString(StaticContent.IntentValue.APPOINTMENT);
            textAppointmentTitleName.setText(appointmentName+" "+"Appointment");

        }

    }
}
