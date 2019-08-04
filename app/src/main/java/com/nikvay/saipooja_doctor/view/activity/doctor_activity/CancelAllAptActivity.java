package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelAllAptActivity extends AppCompatActivity {

    CalendarView calendarView;
    ApiInterface apiInterface;
    String patient_id, date, TAG = getClass().getSimpleName();
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String label = "1", appointmentName, doctor_id, user_id;

    private boolean isStartDate = false;
    String ConvertstartTime, ConvertendTime;

    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;

    String startTime = "09:00", endTime = "21:00";

    int hours, converstartminute, converendminute;

    TextView to_iv_endTime, from_iv_startTime, tv_txt_date, textSave;


    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    ProgressDialog pd;

    private ImageView iv_close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_all_apt);


        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        calendarView = findViewById(R.id.calendarView);
        textSave = findViewById(R.id.textSave);
        to_iv_endTime = findViewById(R.id.to_iv_endTime);
        from_iv_startTime = findViewById(R.id.from_iv_startTime);
        tv_txt_date = findViewById(R.id.tv_txt_date);
        iv_close = findViewById(R.id.iv_close);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        doctorModelArrayList = SharedUtils.getUserDetails(CancelAllAptActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

        errorMessageDialog = new ErrorMessageDialog(CancelAllAptActivity.this);
        successMessageDialog = new SuccessMessageDialog(CancelAllAptActivity.this);
       /* if (NetworkUtils.isNetworkAvailable(CancleAllAptActivity.this))
        {
            CancleAllAptCall(doctor_id,user_id,date);
        }
        else
            NetworkUtils.isNetworkNotAvailable(CancleAllAptActivity.this);*/


    }

    private void events() {

        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (NetworkUtils.isNetworkAvailable(CancelAllAptActivity.this)) {
                    CancelAllAptCall(doctor_id, user_id, date);

                } else
                    NetworkUtils.isNetworkNotAvailable(CancelAllAptActivity.this);

            }
        });
        from_iv_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = true;
                showTimePickerDialog();
            }
        });

        to_iv_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = false;
                showTimePickerDialog();
            }
        });

        tv_txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CancelAllAptActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(day, month, year);

                        long dateAttendance = chosenDate.toMillis(true);
                        strDate = DateFormat.format("yyyy-MM-dd", dateAttendance);

                        date = (String) strDate;
                        tv_txt_date.setText(date);


                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void CancelAllAptCall(String doctor_id, String user_id, String date) {
        Call<SuccessModel> call = apiInterface.cancelAppointment(doctor_id, user_id, date, ConvertstartTime, ConvertendTime);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>>>>>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel serviceModel = response.body();
                        String message = null, errorCode = null;

                        if (serviceModel != null) {
                            message = serviceModel.getMsg();
                            errorCode = serviceModel.getError_code();

                            if (errorCode.equalsIgnoreCase("1")) {
                                successMessageDialog.showDialog("All Appointment Cancelled ");
                            } else {
                                errorMessageDialog.showDialog("Parameter is missing");
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });
    }


    private void showTimePickerDialog() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {

                    try {
                        if (isStartDate) {
                            //  from_iv_startTime.setText((hourOfDay + ":" + minute));

                            hours = hourOfDay * 60;
                            converstartminute = hours + minute;

                            int h = converstartminute / 60 + Integer.parseInt(startTime.substring(0, 1));
                            int m = converstartminute % 60 + Integer.parseInt(startTime.substring(3, 4));
                            ConvertstartTime = h + ":" + m;
                            from_iv_startTime.setText(ConvertstartTime);
                            //  Toast.makeText(BusinessHourActivity.this,"Time in minute"+startTimeSunday,Toast.LENGTH_LONG).show();


                        } else {
                            // to_iv_endTime.setText((hourOfDay + ":" + minute));
                            hours = hourOfDay * 60;
                            converendminute = hours + minute;

                            int h = converendminute / 60 + Integer.parseInt(endTime.substring(0, 1));
                            int m = converendminute % 60 + Integer.parseInt(endTime.substring(3, 4));
                            ConvertendTime = h + ":" + m;
                            to_iv_endTime.setText(ConvertendTime);
                            //  Toast.makeText(BusinessHourActivity.this,"Time in minute"+endTimeSunday,Toast.LENGTH_LONG).show();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(CancelAllAptActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }


}
