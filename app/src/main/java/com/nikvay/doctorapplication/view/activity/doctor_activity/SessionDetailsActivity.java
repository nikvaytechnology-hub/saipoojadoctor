package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.utils.StaticContent;

public class SessionDetailsActivity extends AppCompatActivity {

    private TextView textClass,textDate;
    private EditText textCost,textSeats,textDescription;
    private ClassModel classModel;
    private String mTitle="Class Details";
    private Button btnNext;
    private ImageView iv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        find_All_IDs();
        events();

    }

    private void find_All_IDs() {

        textClass=findViewById(R.id.textClass);
        textDate=findViewById(R.id.textDate);
        textCost=findViewById(R.id.textCost);
        textSeats=findViewById(R.id.textSeats);
        textDescription=findViewById(R.id.textDescription);
        btnNext=findViewById(R.id.btnNext);
        iv_close=findViewById(R.id.iv_close);



        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }

        if (mTitle.equals(StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS)) {
            textClass.setText(classModel.getName());
            //textDuration.setText(classModel.getDuration());
            textCost.setText(classModel.getCost());
            textDescription.setText(classModel.getDescription());
            textDate.setText(classModel.getDate());
            textSeats.setText(classModel.getSeats());
           // btnNext.setText(StaticContent.ButtonContent.UPDATE);
            //class_id=classModel.getClass_id();
        }

    }

    private void events() {

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SessionDetailsActivity.this,ClassTimeSlotActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                startActivity(intent);
            }
        });

    }
}
