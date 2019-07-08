package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.utils.StaticContent;

public class PatientDetailsActivity extends AppCompatActivity {


    private ImageView iv_close, iv_patient_call, iv_patient_service, iv_patient_message, iv_patient_email;
    private TextView textName,textEmail,textContact,textTitleName;
    private RelativeLayout relativeLayoutAppointment,relativeLayoutPrescription,relativeLayoutPayment;
    private PatientModel patientModel;
    String patient_id;
    private Button btnEdit;
    private String mTitle="Add Customer";

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
                Intent intent = new Intent(PatientDetailsActivity.this, AppointmentHistoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, patientModel);
                startActivity(intent);

            }
        });

        relativeLayoutPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PatientDetailsActivity.this, PaymentHistoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, patientModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_EDIT_PATIENT);
                startActivity(intent);


            }
        });

        relativeLayoutPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PatientDetailsActivity.this, PrescriptionHistoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, patientModel);
                startActivity(intent);

            }
        });

        iv_patient_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+textContact.getText().toString().trim()));
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
                Uri uri = Uri.parse("smsto:" + textContact.getText().toString().trim());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                //intent.putExtra("sms_body", "Hello");
                startActivity(intent);

            }
        });

        iv_patient_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ textEmail.getText().toString().trim()});
               // email.putExtra(Intent.EXTRA_SUBJECT,"XYZ");
               // email.putExtra(Intent.EXTRA_TEXT, "XYZ");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientDetailsActivity.this, NewPatientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PATIENT_DETAIL, patientModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_EDIT_PATIENT);
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
        btnEdit = findViewById(R.id.btnEdit);

        relativeLayoutAppointment = findViewById(R.id.relativeLayoutAppointment);
        relativeLayoutPrescription = findViewById(R.id.relativeLayoutPrescription);
        relativeLayoutPayment = findViewById(R.id.relativeLayoutPayment);




        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            patientModel= (PatientModel) bundle.getSerializable(StaticContent.IntentKey.PATIENT_DETAIL);
            patient_id = patientModel.getPatient_id();
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
            textTitleName.setText(mTitle);
        }

        if(mTitle.equals(StaticContent.IntentValue.ACTIVITY_EDIT_PATIENT))
        {
            textName.setText(patientModel.getName());
            textEmail.setText(patientModel.getEmail());
            textContact.setText(patientModel.getPhone_no());
            textTitleName.setText(patientModel.getName());
        }

    }
}
