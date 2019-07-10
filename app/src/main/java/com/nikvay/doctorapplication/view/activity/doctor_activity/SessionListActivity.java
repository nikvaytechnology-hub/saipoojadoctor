package com.nikvay.doctorapplication.view.activity.doctor_activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Filterable;
import android.widget.ImageView;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.ClassModel;
import com.nikvay.doctorapplication.model.SessionListModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.SessionListAdapter;

import java.util.ArrayList;

import io.fabric.sdk.android.services.settings.SessionSettingsData;

public class SessionListActivity extends AppCompatActivity {
    private ImageView iv_close,iv_no_data_found;
    private RecyclerView recyclerSessionList;
    private ArrayList<SessionListModel> sessionListModelArrayList=new ArrayList<>();
    private SessionListAdapter sessionListAdapter;
    private FloatingActionButton fabAddSession;
    private ClassModel classModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        iv_close=findViewById(R.id.iv_close);
        iv_no_data_found=findViewById(R.id.iv_no_data_found);
        recyclerSessionList=findViewById(R.id.recyclerSessionList);
        fabAddSession=findViewById(R.id.fabAddSession);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
        }


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SessionListActivity.this);
        recyclerSessionList.setLayoutManager(linearLayoutManager);
        recyclerSessionList.hasFixedSize();

        for(int i=0;i<=5;i++)
        {

            sessionListModelArrayList.add(new SessionListModel("12-05-2019","12:00","Health Check Up"));
        }

        sessionListAdapter=new SessionListAdapter(SessionListActivity.this,sessionListModelArrayList);
        recyclerSessionList.setAdapter(sessionListAdapter);
        sessionListAdapter.notifyDataSetChanged();

    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fabAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SessionListActivity.this,SessionDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL,classModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                startActivity(intent);
            }
        });

    }
}
