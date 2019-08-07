package com.nikvay.saipoojadoctor.view.activity.doctor_activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.nikvay.saipoojadoctor.model.ClassModel;
import com.nikvay.saipoojadoctor.model.DoctorModel;
import com.nikvay.saipoojadoctor.model.SelectDateTimeModel;
import com.nikvay.saipoojadoctor.model.SuccessModel;
import com.nikvay.saipoojadoctor.utils.ErrorMessageDialog;
import com.nikvay.saipoojadoctor.utils.NetworkUtils;
import com.nikvay.saipoojadoctor.utils.SharedUtils;
import com.nikvay.saipoojadoctor.utils.StaticContent;
import com.nikvay.saipoojadoctor.view.adapter.doctor_adapter.ClassTimeSlotAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassTimeSlotActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerViewTime;
    private ClassTimeSlotAdapter classTimeSlotAdapter;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList = new ArrayList<>();
    private ImageView iv_close;
    private CalendarView calendarView;
    private TextView textSlotNotFound;
    private String date;
    private Spinner schedule;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String  TAG = getClass().getSimpleName(), doctor_id, user_id,selected_day,dayStatus="1";
    private ClassModel classModel;
    SharedPreferences sharedPreferences2;
    private String []spinnerArray={"Morning","Evening","Day"};
    private ArrayAdapter arrayAdapter;
String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time_slot);
        sharedPreferences2=getSharedPreferences("login_status",MODE_PRIVATE);

        find_All_Ids();
        events();
    }


    private void events()
    {
        schedule.setOnItemSelectedListener(this);
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

                if (NetworkUtils.isNetworkAvailable(ClassTimeSlotActivity.this))
                    callTimeSlot();
                else
                    NetworkUtils.isNetworkNotAvailable(ClassTimeSlotActivity.this);
                // Toast.makeText(DateTimeSelectActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void find_All_Ids() {
        schedule=findViewById(R.id.spinnerSchedule);
        arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,spinnerArray);
        schedule.setAdapter(arrayAdapter);

        doctor_id=getIntent().getStringExtra("doctor_id");
        user_id=getIntent().getStringExtra("user_id");
        recyclerViewTime = findViewById(R.id.recyclerViewTime);
        iv_close = findViewById(R.id.iv_close);
        calendarView = findViewById(R.id.calendarView);
        textSlotNotFound = findViewById(R.id.textSlotNotFound);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ClassTimeSlotActivity.this, 3);
        recyclerViewTime.setLayoutManager(gridLayoutManager);
        recyclerViewTime.hasFixedSize();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        errorMessageDialog = new ErrorMessageDialog(ClassTimeSlotActivity.this);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        doctorModelArrayList = SharedUtils.getUserDetails(ClassTimeSlotActivity.this);
       // doctor_id = doctorModelArrayList.get(0).getDoctor_id();
       // user_id = doctorModelArrayList.get(0).getUser_id();



        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);

        }



        if (NetworkUtils.isNetworkAvailable(ClassTimeSlotActivity.this))
            callTimeSlot();
        else
            NetworkUtils.isNetworkNotAvailable(ClassTimeSlotActivity.this);


    }

    private void callTimeSlot()
    {

        status=sharedPreferences2.getString("login_status","");
        if (status.equals("doctor"))
        {

            SharedPreferences sharedPreferences=getSharedPreferences("user_id",MODE_PRIVATE);
            doctor_id=sharedPreferences.getString("doctor_id","");
            user_id=sharedPreferences.getString("user_id","");

        }

        Call<SuccessModel> call = apiInterface.appointmentTimeSlot(dayStatus,date, doctor_id,user_id);
        call.enqueue(new Callback<SuccessModel>()
        {

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
                                    classTimeSlotAdapter = new ClassTimeSlotAdapter(ClassTimeSlotActivity.this, selectDateTimeModelArrayList, date,classModel);
                                    recyclerViewTime.setAdapter(classTimeSlotAdapter);
                                    textSlotNotFound.setVisibility(View.GONE);
                                    classTimeSlotAdapter.notifyDataSetChanged();
                                } else {
                                    textSlotNotFound.setVisibility(View.VISIBLE);
                                    classTimeSlotAdapter.notifyDataSetChanged();
                                }

                            }else if(code.equalsIgnoreCase("2")) {
                                textSlotNotFound.setVisibility(View.VISIBLE);
                                classTimeSlotAdapter.notifyDataSetChanged();
                            }else {
                                textSlotNotFound.setVisibility(View.VISIBLE);
                                classTimeSlotAdapter.notifyDataSetChanged();
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
        selected_day=spinnerArray[position];

        if (selected_day.equalsIgnoreCase("Morning"))
        {
            dayStatus="1";
            callTimeSlot();

        }
        if (selected_day.equalsIgnoreCase("Evening"))
        {
            dayStatus="2";
            callTimeSlot();

        }
        if (selected_day.equalsIgnoreCase("Day"))
        {
            dayStatus="3";
            callTimeSlot();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
