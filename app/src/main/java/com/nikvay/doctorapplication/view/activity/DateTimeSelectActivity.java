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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
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
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList = new ArrayList<>();
    ;
    private ImageView iv_close;
    private CalendarView calendarView;
    private TextView textSlotNotFound;
    private String date;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private ServiceModel serviceModel;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String mTitle = "Service Details", reschedule = "", service_id, TAG = getClass().getSimpleName(), doctor_id, user_id;

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


        doctorModelArrayList = SharedUtils.getUserDetails(DateTimeSelectActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            reschedule = bundle.getString(StaticContent.IntentKey.RESCHEDULE);
            if(reschedule!=null)
            serviceModel = (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            service_id = serviceModel.getService_id();
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }


        if (NetworkUtils.isNetworkAvailable(DateTimeSelectActivity.this))
            callTimeSlot();
        else
            NetworkUtils.isNetworkNotAvailable(DateTimeSelectActivity.this);


    }

    private void callTimeSlot() {


        Call<SuccessModel> call = apiInterface.appointmentTimeSlot(date, doctor_id);


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
                                    selectDateTimeAdapter = new SelectDateTimeAdapter(DateTimeSelectActivity.this, selectDateTimeModelArrayList, serviceModel, date);
                                    recyclerViewTime.setAdapter(selectDateTimeAdapter);
                                    selectDateTimeAdapter.notifyDataSetChanged();
                                } else {
                                    textSlotNotFound.setVisibility(View.VISIBLE);
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
