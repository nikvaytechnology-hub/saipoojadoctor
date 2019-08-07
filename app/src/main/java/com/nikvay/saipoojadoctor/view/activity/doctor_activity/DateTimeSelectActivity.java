package com.nikvay.saipoojadoctor.view.activity.doctor_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.apicallcommon.ApiClient;
import com.nikvay.saipoojadoctor.apicallcommon.ApiInterface;
import com.nikvay.saipoojadoctor.model.AppointmentListModel;
import com.nikvay.saipoojadoctor.model.DoctorModel;
import com.nikvay.saipoojadoctor.model.SelectDateTimeModel;
import com.nikvay.saipoojadoctor.model.ServiceModel;
import com.nikvay.saipoojadoctor.model.SuccessModel;
import com.nikvay.saipoojadoctor.utils.ErrorMessageDialog;
import com.nikvay.saipoojadoctor.utils.NetworkUtils;
import com.nikvay.saipoojadoctor.utils.SharedUtils;
import com.nikvay.saipoojadoctor.utils.StaticContent;
import com.nikvay.saipoojadoctor.view.adapter.doctor_adapter.SelectDateTimeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateTimeSelectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerSchedule;
    private String []spinnerArray={"Morning","Evening","Day"};
    private ArrayAdapter arrayAdapter;

    private RecyclerView recyclerViewTime;
    private SelectDateTimeAdapter selectDateTimeAdapter;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList = new ArrayList<>();
    private ImageView iv_close;
    private CalendarView calendarView;
    private TextView textSlotNotFound;
    private String date;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private ServiceModel serviceModel;


    private AppointmentListModel appoinmentListModel;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String mTitle, reschedule="", service_id, TAG = getClass().getSimpleName(), doctor_id, user_id,ststus_code,schedule_ststus="1";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_select);
        find_All_Ids();
        events();
    }

    private void events() {
        spinnerSchedule.setOnItemSelectedListener(this);
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
                strDate = DateFormat.format("yyyy-MM-dd", dateAppointment);
                date = (String) strDate;

                if (NetworkUtils.isNetworkAvailable(DateTimeSelectActivity.this))
                    callTimeSlot();
                else
                    NetworkUtils.isNetworkNotAvailable(DateTimeSelectActivity.this);
                // Toast.makeText(DateTimeSelectActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void find_All_Ids() {
        spinnerSchedule=findViewById(R.id.spinnerScheduletype);
        arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,spinnerArray);
        spinnerSchedule.setAdapter(arrayAdapter);

        recyclerViewTime = findViewById(R.id.recyclerViewTime);
        iv_close = findViewById(R.id.iv_close);
        calendarView = findViewById(R.id.calendarView);
        textSlotNotFound = findViewById(R.id.textSlotNotFound);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DateTimeSelectActivity.this, 3);
        recyclerViewTime.setLayoutManager(gridLayoutManager);
        recyclerViewTime.hasFixedSize();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        errorMessageDialog = new ErrorMessageDialog(DateTimeSelectActivity.this);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        doctorModelArrayList = SharedUtils.getUserDetails(DateTimeSelectActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
            if (mTitle.equalsIgnoreCase(StaticContent.IntentValue.RESCHEDULE)) {
                reschedule = StaticContent.IntentKey.RESCHEDULE;
                appoinmentListModel= (AppointmentListModel) bundle.getSerializable(StaticContent.IntentKey.APPOINTMENT);
            } else {
                serviceModel = (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
                service_id = serviceModel.getService_id();
            }

        }


        if (NetworkUtils.isNetworkAvailable(DateTimeSelectActivity.this))
            callTimeSlot();
        else
            NetworkUtils.isNetworkNotAvailable(DateTimeSelectActivity.this);


    }

    private void callTimeSlot() {

        Call<SuccessModel> call = apiInterface.appointmentTimeSlot(schedule_ststus,date, doctor_id,user_id);


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

                            if (code.equalsIgnoreCase("1")) {
                               selectDateTimeModelArrayList = successModel.getSelectDateTimeModelArrayList();
                                if (selectDateTimeModelArrayList.size() != 0) {
                                    selectDateTimeAdapter = new SelectDateTimeAdapter(DateTimeSelectActivity.this, selectDateTimeModelArrayList, serviceModel, date, reschedule,appoinmentListModel);
                                    recyclerViewTime.setAdapter(selectDateTimeAdapter);
                                    textSlotNotFound.setVisibility(View.GONE);
                                    selectDateTimeAdapter.notifyDataSetChanged();
                                } else
                                {
                                    textSlotNotFound.setVisibility(View.VISIBLE);
                                    selectDateTimeAdapter.notifyDataSetChanged();
                                }

                            }
                            else if(code.equalsIgnoreCase("2")) {
                                textSlotNotFound.setVisibility(View.VISIBLE);
                                selectDateTimeAdapter.notifyDataSetChanged();
                            }
                            else {
                                textSlotNotFound.setVisibility(View.VISIBLE);
                                selectDateTimeAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        ststus_code=spinnerArray[position];
        if (ststus_code.equalsIgnoreCase("Morning"))
        {
            schedule_ststus="1";
            selectDateTimeModelArrayList.clear();
            callTimeSlot();
        }
        if (ststus_code.equalsIgnoreCase("Evening"))
        {
            schedule_ststus="2";
            selectDateTimeModelArrayList.clear();
            callTimeSlot();
        }
        if (ststus_code.equalsIgnoreCase("Day"))
        {
            schedule_ststus="3";
            selectDateTimeModelArrayList.clear();
            callTimeSlot();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
