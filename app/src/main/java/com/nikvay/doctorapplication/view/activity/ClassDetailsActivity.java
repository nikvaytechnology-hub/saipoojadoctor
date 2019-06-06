package com.nikvay.doctorapplication.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;

public class ClassDetailsActivity extends AppCompatActivity {

    private TextView textClass,textCost,textDate,textSeats,textDescription,textEdit;
    private ImageView iv_close;
    private ClassModel classModel;
    private String mTitle="Service Details";
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

        textEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent intent = new Intent(ClassDetailsActivity.this, NewClassActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE,StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                startActivity(intent);


            }
        });

    }

    private void find_All_IDs() {
        textClass=findViewById(R.id.textClass);
        textCost=findViewById(R.id.textCost);
        textDate=findViewById(R.id.textDate);
        textSeats=findViewById(R.id.textSeats);
        textDescription=findViewById(R.id.textDescription);
        iv_close=findViewById(R.id.iv_close);
        textEdit=findViewById(R.id.textEdit);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            classModel= (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }

        if(mTitle.equals(StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS))
        {
            textClass.setText(classModel.getName());
            textCost.setText(classModel.getCost());
            textDate.setText(classModel.getDate());
            textSeats.setText(classModel.getSeats());
            textDescription.setText(classModel.getDescription());
        }
    }
}