package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.AppoinmentListModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;

public class AppointmentDetailsActivity extends AppCompatActivity {

    private AppoinmentListModel appoinmentListModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        find_All_IDs();
        events();

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            appoinmentListModel= (AppoinmentListModel) bundle.getSerializable(StaticContent.IntentKey.APPOINTMENT);
            Toast.makeText(this, appoinmentListModel.getDate(), Toast.LENGTH_SHORT).show();
        }

    }

    private void events() {

    }

    private void find_All_IDs() {

    }
}
