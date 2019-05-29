package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.SelectDateTimeModel;
import com.nikvay.doctorapplication.view.adapter.PatientAdapter;
import com.nikvay.doctorapplication.view.adapter.SelectDateTimeAdapter;

import java.util.ArrayList;

public class DateTimeSelectActivity extends AppCompatActivity {


    private RecyclerView recyclerViewTime;
    private SelectDateTimeAdapter selectDateTimeAdapter;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList=new ArrayList<>();
    private ImageView  iv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_select);
        find_All_Ids();
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

    private void find_All_Ids() {
        recyclerViewTime=findViewById(R.id.recyclerViewTime);
        iv_close=findViewById(R.id.iv_close);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DateTimeSelectActivity.this,3);
        recyclerViewTime.setLayoutManager(gridLayoutManager);


        for (int i=0;i<=20;i++)
        {

         selectDateTimeModelArrayList.add(new SelectDateTimeModel("10 AM"));
        }
        selectDateTimeAdapter = new SelectDateTimeAdapter(DateTimeSelectActivity.this,selectDateTimeModelArrayList);
        recyclerViewTime.setAdapter(selectDateTimeAdapter);
        recyclerViewTime.addItemDecoration(new DividerItemDecoration(DateTimeSelectActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerViewTime.addItemDecoration(new DividerItemDecoration(DateTimeSelectActivity.this, DividerItemDecoration.VERTICAL));
        recyclerViewTime.setHasFixedSize(true);
    }
}
