package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.utils.StaticContent;

public class ClassDetailsActivity extends AppCompatActivity {

    private TextView textClass,textCost,textSeats,textDescription;
    private ImageView iv_close;
    private Button btnEdit;
    TextView tvSessionCount,date_id;
    private ClassModel classModel;
    private String mTitle="Class Details";
    private RelativeLayout relativeLayoutSession;
    String class_id,class_name,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassDetailsActivity.this, NewClassActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                startActivity(intent);


            }
        });
        relativeLayoutSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(ClassDetailsActivity.this,SessionListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("class_id",class_id);
                intent.putExtra("class_name",class_name);

                intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                startActivity(intent);
            }
        });

    }

    private void find_All_IDs() {
        class_id=getIntent().getStringExtra("class_id");
         class_name = getIntent().getStringExtra("class_name");
        SharedPreferences sharedPreferences=getSharedPreferences("class_name", Context.MODE_PRIVATE);
        String count=sharedPreferences.getString("count","");
        String date=sharedPreferences.getString("date","");

        date_id=findViewById(R.id.date_id);
        date_id.setText(date);
        textClass=findViewById(R.id.textClass);
        textClass.setText(class_name);
        tvSessionCount=findViewById(R.id.tvsessionCount);
        tvSessionCount.setText(count);
       // textCost=findViewById(R.id.textCost);
        textSeats=findViewById(R.id.textSeats);
        textDescription=findViewById(R.id.textDescription);
        iv_close=findViewById(R.id.iv_close);
        btnEdit=findViewById(R.id.btnEdit);
        relativeLayoutSession=findViewById(R.id.relativeLayoutSession);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            classModel= (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }

        if(mTitle.equals(StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS))
        {
       //     textCost.setText(classModel.getCost());
           // textDate.setText(classModel.getDate());
            textSeats.setText(classModel.getSeats());
            textDescription.setText(classModel.getDescription());
        }
    }
}
