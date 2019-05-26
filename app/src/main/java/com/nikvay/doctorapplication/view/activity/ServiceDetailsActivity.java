package com.nikvay.doctorapplication.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;

public class ServiceDetailsActivity extends AppCompatActivity {


    private  TextView textService,textDuration,textCost;
    private ImageView iv_close;
    private String mTitle="Service Details";
    private ServiceModel serviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_deatils);

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


    }

    private void find_All_IDs() {
        iv_close = findViewById(R.id.iv_close);
        textService = findViewById(R.id.textService);
        textDuration = findViewById(R.id.textDuration);
        textCost = findViewById(R.id.textCost);


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }

        if(mTitle.equals(StaticContent.IntentValue.ACTIVITY_SERVICE_DETAILS))
        {
            textService.setText(serviceModel.getS_name());
            textDuration.setText(serviceModel.getService_time());
            textCost.setText(serviceModel.getService_cost());
        }

    }
}
