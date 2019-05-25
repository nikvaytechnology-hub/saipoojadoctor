package com.nikvay.doctorapplication.view.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.view.adapter.PatientAdapter;
import com.nikvay.doctorapplication.view.adapter.ServiceListAdapter;

import java.util.ArrayList;

public class ServiceListActivity extends AppCompatActivity {


    private RecyclerView recyclerViewServiceList;
    ArrayList<ServiceModel> serviceModelArrayList=new ArrayList<>();
    private ServiceListAdapter serviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);


        find_All_IDs();
        events();
    }

    private void events() {

    }

    private void find_All_IDs() {

        recyclerViewServiceList=findViewById(R.id.recyclerPatientList);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ServiceListActivity.this);
        recyclerViewServiceList.setLayoutManager(linearLayoutManager);

        for(int i=1;i<=5;i++)
        {

            serviceModelArrayList.add(new ServiceModel("1","Simple Demo","60","50"));
        }

        serviceListAdapter=new ServiceListAdapter(ServiceListActivity.this,serviceModelArrayList);
        recyclerViewServiceList.setAdapter(serviceListAdapter);
        recyclerViewServiceList.addItemDecoration(new DividerItemDecoration(ServiceListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerViewServiceList.setHasFixedSize(true);


    }
}
