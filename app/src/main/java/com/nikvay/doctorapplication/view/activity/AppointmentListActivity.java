package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.AppoinmentListModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.adapter.AppointmentListAdapter;

import java.util.ArrayList;

public class AppointmentListActivity extends AppCompatActivity {


    private String status, appointmentName;
    private ImageView iv_close;
    private TextView textAppointmentTitleName;
    private RecyclerView recyclerViewAppointmentList;
    private ArrayList<AppoinmentListModel> appoinmentListModelArrayList = new ArrayList<>();
    private AppointmentListAdapter appointmentListAdapter;

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
        iv_close = findViewById(R.id.iv_close);
        textAppointmentTitleName = findViewById(R.id.textAppointmentTitleName);
        recyclerViewAppointmentList = findViewById(R.id.recyclerViewAppointmentList);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            status = bundle.getString(StaticContent.IntentKey.STATUS);
            appointmentName = bundle.getString(StaticContent.IntentKey.APPOINTMENT);
            textAppointmentTitleName.setText(appointmentName + " " + "Appointment");

        }


        recyclerViewAppointmentList.setHasFixedSize(true);
        recyclerViewAppointmentList.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i <= 9; i++) {
            appoinmentListModelArrayList.add(new AppoinmentListModel("Monday 23 May", " ", "12:00", "dhananjay", "60 Minute", "health check up","60RS"));
        }

        appointmentListAdapter=new AppointmentListAdapter(AppointmentListActivity.this,appoinmentListModelArrayList);
        recyclerViewAppointmentList.setAdapter(appointmentListAdapter);
        appointmentListAdapter.notifyDataSetChanged();

    }
}
