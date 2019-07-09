package com.nikvay.doctorapplication.view.activity.admin_doctor_activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.nikvay.doctorapplication.R;

public class AdminClassListActivity extends AppCompatActivity {

    private ImageView  iv_close;
    private SwipeRefreshLayout refreshClass;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_class_list);

        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        iv_close=findViewById(R.id.iv_close);
        refreshClass=findViewById(R.id.refreshClass);
        recyclerView=findViewById(R.id.recyclerView);
    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        refreshClass.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }
}
