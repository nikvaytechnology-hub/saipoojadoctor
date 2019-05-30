package com.nikvay.doctorapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.SelectDateTimeModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.adapter.PatientAdapter;
import com.nikvay.doctorapplication.view.adapter.SelectDateTimeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateTimeSelectActivity extends AppCompatActivity {


    private RecyclerView recyclerViewTime;
    private SelectDateTimeAdapter selectDateTimeAdapter;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList=new ArrayList<>();
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayListSend=new ArrayList<>();
    private ImageView  iv_close;
    private CalendarView calendarView;
    private String date,selectedDate;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private ServiceModel serviceModel;
    private String mTitle="Service Details",service_id,TAG = getClass().getSimpleName();

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
                strDate = DateFormat.format("yyyy-mm-dd", dateAppointment);
                date= (String) strDate;

                if (NetworkUtils.isNetworkAvailable(DateTimeSelectActivity.this))
                    callTimeSlot();
                else
                    NetworkUtils.isNetworkNotAvailable(DateTimeSelectActivity.this);
               // Toast.makeText(DateTimeSelectActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void find_All_Ids() {
        recyclerViewTime=findViewById(R.id.recyclerViewTime);
        iv_close=findViewById(R.id.iv_close);
        calendarView=findViewById(R.id.calendarView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DateTimeSelectActivity.this,3);
        recyclerViewTime.setLayoutManager(gridLayoutManager);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        date = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(new Date());
        errorMessageDialog= new ErrorMessageDialog(DateTimeSelectActivity.this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            service_id=serviceModel.getService_id();
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }


        if (NetworkUtils.isNetworkAvailable(DateTimeSelectActivity.this))
            callTimeSlot();
        else
            NetworkUtils.isNetworkNotAvailable(DateTimeSelectActivity.this);


    }
    private void callTimeSlot() {


        Call<SuccessModel> call = apiInterface.appointmentTimeSlot(service_id,date);


        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();

                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();
                            selectDateTimeModelArrayList.clear();
                            selectDateTimeModelArrayListSend.clear();
                            
                            if (code.equalsIgnoreCase("1")) {
                                selectDateTimeModelArrayList=successModel.getSelectDateTimeModelArrayList();

                                for(int i=0;i<selectDateTimeModelArrayList.size();i++)
                                {
                                   SelectDateTimeModel selectDateTimeModel=selectDateTimeModelArrayList.get(i);
                                    if(selectDateTimeModel.getStatus().equals("0"))
                                    {
                                        selectDateTimeModelArrayListSend.add(selectDateTimeModelArrayList.get(i));
                                    }
                                }
                                if(selectDateTimeModelArrayListSend.size()!=0) {
                                    selectDateTimeAdapter = new SelectDateTimeAdapter(DateTimeSelectActivity.this, selectDateTimeModelArrayListSend, serviceModel, date);
                                    recyclerViewTime.setAdapter(selectDateTimeAdapter);
                                    recyclerViewTime.addItemDecoration(new DividerItemDecoration(DateTimeSelectActivity.this, DividerItemDecoration.HORIZONTAL));
                                    recyclerViewTime.addItemDecoration(new DividerItemDecoration(DateTimeSelectActivity.this, DividerItemDecoration.VERTICAL));
                                    recyclerViewTime.setHasFixedSize(true);
                                }
                                else
                                {

                                }

                            } else {
                                errorMessageDialog.showDialog("List Not Found");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                errorMessageDialog.showDialog(t.getMessage());
            }
        });

    }

}
