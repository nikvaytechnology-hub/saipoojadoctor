package com.nikvay.doctorapplication.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;

public class PatientDetailsActivity extends AppCompatActivity {


    private ImageView iv_close, iv_patient_call, iv_patient_service, iv_patient_message, iv_patient_email;
    private TextView textName,textEmail,textContact,textTitleName,textEdit;
    private RelativeLayout relativeLayoutAppointment,relativeLayoutPrescription,relativeLayoutPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

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

        relativeLayoutAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientDetailsActivity.this, "Appointment", Toast.LENGTH_SHORT).show();
            }
        });

        relativeLayoutPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientDetailsActivity.this, "Payment", Toast.LENGTH_SHORT).show();
            }
        });

        relativeLayoutPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientDetailsActivity.this, "Prescription", Toast.LENGTH_SHORT).show();
            }
        });

        iv_patient_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientDetailsActivity.this, "Patient Call", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+"9503873045"));
                startActivity(intent);
            }
        });

        iv_patient_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientDetailsActivity.this, "Service", Toast.LENGTH_SHORT).show();
            }
        });

        iv_patient_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientDetailsActivity.this, "Patient Message", Toast.LENGTH_SHORT).show();


            }
        });

        iv_patient_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatientDetailsActivity.this, "Patient Email", Toast.LENGTH_SHORT).show();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "jatharnihalp@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,"XYZ");
                email.putExtra(Intent.EXTRA_TEXT, "XYZ");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        textEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientDetailsActivity.this,NewPatientActivity.class);
                intent.putExtra("EDIT","1");
                startActivity(intent);
            }
        });



    }

    private void find_All_IDs() {
        iv_close = findViewById(R.id.iv_close);
        iv_patient_call = findViewById(R.id.iv_patient_call);
        iv_patient_service = findViewById(R.id.iv_patient_service);
        iv_patient_message = findViewById(R.id.iv_patient_message);
        iv_patient_email = findViewById(R.id.iv_patient_email);

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textContact = findViewById(R.id.textContact);
        textTitleName = findViewById(R.id.textTitleName);
        textEdit = findViewById(R.id.textEdit);

        relativeLayoutAppointment = findViewById(R.id.relativeLayoutAppointment);
        relativeLayoutPrescription = findViewById(R.id.relativeLayoutPrescription);
        relativeLayoutPayment = findViewById(R.id.relativeLayoutPayment);

    }
}
