package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.model.SelectDateTimeModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.adapter.PatientAdapter;
import com.nikvay.doctorapplication.view.adapter.SelectDateTimeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DateTimeSelectActivity extends AppCompatActivity {


    private RecyclerView recyclerViewTime;
    private SelectDateTimeAdapter selectDateTimeAdapter;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList=new ArrayList<>();
    private ImageView  iv_close;
    private CalendarView calendarView;
    private String date,selectedDate;
    private ServiceModel serviceModel;
    private String mTitle="Service Details";

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

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                CharSequence strDate = null;
                Time chosenDate = new Time();
                chosenDate.set(dayOfMonth, month, year);

                long dateAppointment = chosenDate.toMillis(true);
                strDate = DateFormat.format("dd-MM-yyyy", dateAppointment);
                date= (String) strDate;
                callAdapter(date);
                Toast.makeText(DateTimeSelectActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void callAdapter(String date) {

    }

    private void find_All_Ids() {
        recyclerViewTime=findViewById(R.id.recyclerViewTime);
        iv_close=findViewById(R.id.iv_close);
        calendarView=findViewById(R.id.calendarView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DateTimeSelectActivity.this,3);
        recyclerViewTime.setLayoutManager(gridLayoutManager);
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }



        for (int i=0;i<=20;i++)
        {

         selectDateTimeModelArrayList.add(new SelectDateTimeModel("10 AM"));
        }
        selectDateTimeAdapter = new SelectDateTimeAdapter(DateTimeSelectActivity.this,selectDateTimeModelArrayList,serviceModel,date);
        recyclerViewTime.setAdapter(selectDateTimeAdapter);
        recyclerViewTime.addItemDecoration(new DividerItemDecoration(DateTimeSelectActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerViewTime.addItemDecoration(new DividerItemDecoration(DateTimeSelectActivity.this, DividerItemDecoration.VERTICAL));
        recyclerViewTime.setHasFixedSize(true); selectDateTimeAdapter = new SelectDateTimeAdapter(DateTimeSelectActivity.this,selectDateTimeModelArrayList,serviceModel,date);
        recyclerViewTime.setAdapter(selectDateTimeAdapter);
        recyclerViewTime.addItemDecoration(new DividerItemDecoration(DateTimeSelectActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerViewTime.addItemDecoration(new DividerItemDecoration(DateTimeSelectActivity.this, DividerItemDecoration.VERTICAL));
        recyclerViewTime.setHasFixedSize(true);
    }
}
