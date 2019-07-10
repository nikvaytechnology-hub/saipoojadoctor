package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.utils.StaticContent;

public class CreateSessionActivity extends AppCompatActivity {

    private ImageView iv_close;
    private TextView textPending,textConfirm,textLabelName;
    private Button btnCreate;
    private String  label="1",date="",time="";
    private String mTitle="Class Details";
    private ClassModel classModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        find_All_ID();
        event();
    }

    private void find_All_ID() {
        iv_close=findViewById(R.id.iv_close);
        textPending=findViewById(R.id.textPending);
        textConfirm=findViewById(R.id.textConfirm);
        btnCreate=findViewById(R.id.btnCreate);
        textLabelName=findViewById(R.id.textLabelName);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
            time = bundle.getString(StaticContent.IntentKey.TIME);
            date = bundle.getString(StaticContent.IntentKey.DATE);
            Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        }

        if (mTitle.equals(StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS)) {
           // textClass.setText(classModel.getName());
            //textDuration.setText(classModel.getDuration());
           // textCost.setText(classModel.getCost());
           // textDescription.setText(classModel.getDescription());
           // textDate.setText(classModel.getDate());
           // textSeats.setText(classModel.getSeats());
            // btnNext.setText(StaticContent.ButtonContent.UPDATE);
            //class_id=classModel.getClass_id();
        }


        textLabelName.setText("Confirm");
    }

    private void event() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        textPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label="0";
                textLabelName.setText("Pending");
                textLabelName.setTextColor(getResources().getColor(R.color.black));

            }
        });
        textConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label="1";
                textLabelName.setText("Confirm");
                textLabelName.setTextColor(getResources().getColor(R.color.confirm));
            }
        });

    }
}
