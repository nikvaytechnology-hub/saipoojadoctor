package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.admin_doctor_activity.AddInstructor;

public class SessionDetailsActivity extends AppCompatActivity {

    private TextView textClass;
    private EditText textCost,textSeats,textDescription;
    private ClassModel classModel;
    private String mTitle="Class Details",cost,no_of_student,description,seats;
    private Button btnNext;
    private ImageView iv_close;
    String doctor_id,user_id;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    SharedPreferences sharedPreferences1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);
SharedPreferences sharedPreferences=getSharedPreferences("Class_name",MODE_PRIVATE);
 sharedPreferences2=getSharedPreferences("session_details",MODE_PRIVATE);
        find_All_IDs();
        events();

    }

    private void find_All_IDs() {

        doctor_id=getIntent().getStringExtra("doctor_id");
        user_id=getIntent().getStringExtra("user_id");
        textClass=findViewById(R.id.textClass);
      //  textClass.setText(sharedPreferences.getString("class_name",""));
        //textDate=findViewById(R.id.textDate);
        textCost=findViewById(R.id.textCost);
        textSeats=findViewById(R.id.textSeats);
        textDescription=findViewById(R.id.textDescription);
        btnNext=findViewById(R.id.btnNext);
        iv_close=findViewById(R.id.iv_close);


/*
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }
*/

/*
        if (mTitle.equals(StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS)) {
            textClass.setText(classModel.getName());
            //textDuration.setText(classModel.getDuration());
            textCost.setText(classModel.getCost());
            textDescription.setText(classModel.getDescription());
            //textDate.setText(classModel.getDate());
            textSeats.setText(classModel.getSeats());
           // btnNext.setText(StaticContent.ButtonContent.UPDATE);
            //class_id=classModel.getClass_id();
        }
*/

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
                cost=textCost.getText().toString();
                seats=textSeats.getText().toString();
                description=textDescription.getText().toString();
                SharedPreferences.Editor editor=sharedPreferences2.edit();
                editor.putString("cost",cost);
                editor.putString("seats",seats);
                editor.putString("description",description);
                editor.apply();
                editor.commit();
                sharedPreferences1=getSharedPreferences("login_status",MODE_PRIVATE);
                String status=sharedPreferences1.getString("login_status","");
                if(status.equals("admin"))
                {
                    Toast.makeText(SessionDetailsActivity.this, status+"", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SessionDetailsActivity.this, AddInstructor.class);
                    intent.putExtra("doctor_id",doctor_id);
                    intent.putExtra("user_id",user_id);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //  intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                    //  intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(SessionDetailsActivity.this,ClassTimeSlotActivity.class);
                    intent.putExtra("doctor_id",doctor_id);
                    intent.putExtra("user_id",user_id);
                    Toast.makeText(SessionDetailsActivity.this, doctor_id+""+user_id, Toast.LENGTH_SHORT).show();
                 /*    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                      intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                 */   startActivity(intent);
                }
            }
        });

    }
}
