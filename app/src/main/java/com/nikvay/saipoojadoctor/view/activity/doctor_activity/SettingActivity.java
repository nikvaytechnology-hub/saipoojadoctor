package com.nikvay.saipoojadoctor.view.activity.doctor_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.apicallcommon.ApiClient;
import com.nikvay.saipoojadoctor.apicallcommon.ApiInterface;
import com.nikvay.saipoojadoctor.utils.StaticContent;

public class SettingActivity extends AppCompatActivity {

ApiInterface apiInterface;
    private ImageView iv_close;
    RelativeLayout rel_business,rel_ll_appoinments,rel_ll_morning,rel_ll_Day,rel_ll_Evening;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        iv_close=findViewById(R.id.iv_close);

        rel_ll_Evening = findViewById(R.id.rel_ll_Evening);
        rel_ll_Day = findViewById(R.id.rel_ll_Day);
        rel_ll_morning = findViewById(R.id.rel_ll_morning);


    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rel_ll_Evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent= new Intent(SettingActivity.this,BusinessHourActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("day_Status","2");

                intent.putExtra(StaticContent.IntentKey.TIME_SLOT, "2");
                startActivity(intent);
            }
        });
        rel_ll_Day.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(SettingActivity.this,BusinessHourActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("day_Status","3");
                intent.putExtra(StaticContent.IntentKey.TIME_SLOT, "3");
                startActivity(intent);
            }
        });

        rel_ll_morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SettingActivity.this,BusinessHourActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("day_Status","1");

                intent.putExtra(StaticContent.IntentKey.TIME_SLOT, "1");
                startActivity(intent);
            }
        });


    }
}
