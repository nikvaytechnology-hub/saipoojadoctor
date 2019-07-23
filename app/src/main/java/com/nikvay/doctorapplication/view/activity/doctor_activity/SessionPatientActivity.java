package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientModel;

public class SessionPatientActivity extends AppCompatActivity
{
    private ImageView iv_close, iv_patient_call, iv_patient_service, iv_patient_message, iv_patient_email;
    private TextView textName,textEmail,textContact,textTitleName;
    private RelativeLayout relativeLayoutAppointment,relativeLayoutPrescription,relativeLayoutPayment;
    private PatientModel patientModel;
    String patient_id;
    private Button btnEdit;
    private String mTitle="Add Customer";

    String email,mno,name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_patient);

        all_ids();
    }

    private void all_ids()
    {
        email=getIntent().getStringExtra("email");
        mno=getIntent().getStringExtra("mno");
        name=getIntent().getStringExtra("name");

        iv_close = findViewById(R.id.iv_close);
        iv_patient_call = findViewById(R.id.iv_patient_call);
        iv_patient_service = findViewById(R.id.iv_patient_service);
        iv_patient_message = findViewById(R.id.iv_patient_message);
        iv_patient_email = findViewById(R.id.iv_patient_email);

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textContact = findViewById(R.id.textContact);
        textTitleName = findViewById(R.id.textTitleName);
        btnEdit = findViewById(R.id.btnEdit);

        textName.setText(name);
        textEmail.setText(email);
        textContact.setText(mno);
        relativeLayoutAppointment = findViewById(R.id.relativeLayoutAppointment);
        relativeLayoutPrescription = findViewById(R.id.relativeLayoutPrescription);
        relativeLayoutPayment = findViewById(R.id.relativeLayoutPayment);

    }
}
