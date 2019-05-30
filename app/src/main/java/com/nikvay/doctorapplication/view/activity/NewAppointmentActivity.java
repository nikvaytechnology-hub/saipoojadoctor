package com.nikvay.doctorapplication.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;

public class NewAppointmentActivity extends AppCompatActivity {


    private ImageView iv_close;
    private TextView textDone,textDateDay,textTime,textPatientName,textEmail,textContact,textServiceName,textDuration,textServiceCost;
    private ServiceModel serviceModel;
    private PatientModel patientModel;
    private  String date="",time="",service_id,patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        find_All_IDs();
        events();
    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(NewAppointmentActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void find_All_IDs() {
        iv_close=findViewById(R.id.iv_close);
        textDone=findViewById(R.id.textDone);
        textDateDay=findViewById(R.id.textDateDay);
        textTime=findViewById(R.id.textTime);
        textPatientName=findViewById(R.id.textPatientName);
        textEmail=findViewById(R.id.textEmail);
        textContact=findViewById(R.id.textContact);
        textServiceName=findViewById(R.id.textServiceName);
        textDuration=findViewById(R.id.textDuration);
        textServiceCost=findViewById(R.id.textServiceCost);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            patientModel= (PatientModel) bundle.getSerializable(StaticContent.IntentKey.PATIENT_DETAIL);
            date=bundle.getString(StaticContent.IntentKey.DATE);
            time=bundle.getString(StaticContent.IntentKey.TIME);
            service_id=serviceModel.getService_id();
            patient_id=patientModel.getPatient_id();

            textServiceName.setText(serviceModel.getS_name());
            textDuration.setText(serviceModel.getService_time());
            textServiceCost.setText(serviceModel.getService_cost());

            textPatientName.setText(patientModel.getName());
            textEmail.setText(patientModel.getEmail());
            textContact.setText(patientModel.getPhone_no());

            textDateDay.setText(date);
            textTime.setText(time+" "+"->"+" "+serviceModel.getService_time());

            Toast.makeText(this, serviceModel.getS_name()+""+patientModel.getPatient_id()+" "+date+" "+time, Toast.LENGTH_SHORT).show();

        }



    }
}
