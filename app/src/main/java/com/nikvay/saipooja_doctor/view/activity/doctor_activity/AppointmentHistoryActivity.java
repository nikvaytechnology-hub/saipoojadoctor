package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.PatientAppointmentHistoryModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.utils.SuccessMessageDialog;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.PatientAppointmentHistoryAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentHistoryActivity extends AppCompatActivity
{

    private RecyclerView recyclerView_appointment;
    private ImageView iv_back, iv_date, iv_empty_list;
    private String date = "", doctor_id, patient_id, user_id,TAG = getClass().getSimpleName();;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ShowProgress showProgress;
    private ApiInterface apiInterface;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ArrayList<PatientAppointmentHistoryModel> patientAppointmentHistoryModelArrayList=new ArrayList<>();
    private PatientAppointmentHistoryAdapter patientAppointmentHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);

        find_All_Id();
        event();
    }

    private void find_All_Id() {
        recyclerView_appointment = findViewById(R.id.recyclerView_appointment);
        iv_back = findViewById(R.id.iv_back);
        iv_date = findViewById(R.id.iv_date);
        iv_empty_list = findViewById(R.id.iv_empty_list);


        errorMessageDialog = new ErrorMessageDialog(AppointmentHistoryActivity.this);
        successMessageDialog = new SuccessMessageDialog(AppointmentHistoryActivity.this);
        showProgress = new ShowProgress(AppointmentHistoryActivity.this);

        iv_empty_list = findViewById(R.id.iv_empty_list);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            patient_id =bundle.getString(StaticContent.IntentKey.PATIENT_DETAIL);
        }

        //Toast.makeText(this, patient_id, Toast.LENGTH_SHORT).show();

        doctorModelArrayList = SharedUtils.getUserDetails(AppointmentHistoryActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppointmentHistoryActivity.this);
        recyclerView_appointment.setLayoutManager(linearLayoutManager);
        recyclerView_appointment.setHasFixedSize(true);


        if (NetworkUtils.isNetworkAvailable(AppointmentHistoryActivity.this))
            appointmentListCall();
        else
            NetworkUtils.isNetworkNotAvailable(AppointmentHistoryActivity.this);



    }

    private void event() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentHistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(day, month, year);

                        long dateAttendance = chosenDate.toMillis(true);
                        strDate = DateFormat.format("yyyy-MM-dd", dateAttendance);

                        date = (String) strDate;

                        if (NetworkUtils.isNetworkAvailable(AppointmentHistoryActivity.this))
                            appointmentListCall();
                        else
                            NetworkUtils.isNetworkNotAvailable(AppointmentHistoryActivity.this);

                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });


    }

    private void appointmentListCall()
    {
        if (patient_id==null)
        {
            patient_id=getIntent().getStringExtra("patient_id");
        }

        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.patientAppointmentHistory(doctor_id,patient_id, date);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response)
            {
                showProgress.dismissDialog();

                String str_response = new Gson().toJson(response.body());
                Log.e(""+TAG,"Response>>>>>>>>>>"+str_response);

                try
                {
                    if (response.isSuccessful())
                    {
                        SuccessModel successModel = response.body();
                        String errorCode = null,msg= null;

                        if (successModel != null)
                        {
                            errorCode =successModel.getError_code();
                            msg = successModel.getMsg();
                            patientAppointmentHistoryModelArrayList.clear();
                            if (errorCode.equalsIgnoreCase("1"))
                            {
                                patientAppointmentHistoryModelArrayList = successModel.getPatientAppointmentHistoryModelArrayList();

                                if (patientAppointmentHistoryModelArrayList.size()!= 0)
                                {
                                    patientAppointmentHistoryAdapter = new PatientAppointmentHistoryAdapter(AppointmentHistoryActivity.this,patientAppointmentHistoryModelArrayList);
                                    recyclerView_appointment.setAdapter(patientAppointmentHistoryAdapter);
                                    patientAppointmentHistoryAdapter.notifyDataSetChanged();

                                    recyclerView_appointment.setVisibility(View.VISIBLE);
                                    iv_empty_list.setVisibility(View.GONE);
                                } else {
                                    recyclerView_appointment.setVisibility(View.GONE);
                                    iv_empty_list.setVisibility(View.VISIBLE);

                                }
                            }
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();

            }
        });
    }
}
