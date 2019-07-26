package com.nikvay.doctorapplication.view.activity.doctor_activity;

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
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.PatientPrescriptionHistoryModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.PrescriptionHistoryAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView_prescription;
    private ImageView iv_back,iv_date,iv_empty_list;
    private String date="",doctor_id,patient_id,user_id,TAG = getClass().getSimpleName();
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    private ShowProgress showProgress;
    private PatientModel patientModel;
    private ApiInterface apiInterface;
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private ArrayList<PatientPrescriptionHistoryModel> patientPrescriptionHistoryModelArrayList=new ArrayList<>();
    private PrescriptionHistoryAdapter prescriptionHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_history);


        find_All_Id();
        event();
    }

    private void find_All_Id() {
        recyclerView_prescription=findViewById(R.id.recyclerView_prescription);
        iv_back=findViewById(R.id.iv_back);
        iv_date=findViewById(R.id.iv_date);
        iv_empty_list=findViewById(R.id.iv_empty_list);


        errorMessageDialog = new ErrorMessageDialog(PrescriptionHistoryActivity.this);
        successMessageDialog = new SuccessMessageDialog(PrescriptionHistoryActivity.this);
        showProgress=new ShowProgress(PrescriptionHistoryActivity.this);

        iv_empty_list = findViewById(R.id.iv_empty_list);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);



        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            patientModel = (PatientModel) bundle.getSerializable(StaticContent.IntentKey.PATIENT_DETAIL);
         //   patient_id =patientModel.getPatient_id();
        }

       // Toast.makeText(this, patient_id, Toast.LENGTH_SHORT).show();


        doctorModelArrayList = SharedUtils.getUserDetails(PrescriptionHistoryActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PrescriptionHistoryActivity.this);
        recyclerView_prescription.setLayoutManager(linearLayoutManager);
        recyclerView_prescription.setHasFixedSize(true);



        if (NetworkUtils.isNetworkAvailable(PrescriptionHistoryActivity.this))
            prescriptionListCall();
        else
            NetworkUtils.isNetworkNotAvailable(PrescriptionHistoryActivity.this);



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

                DatePickerDialog datePickerDialog = new DatePickerDialog(PrescriptionHistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(day, month, year);

                        long dateAttendance = chosenDate.toMillis(true);
                        strDate = DateFormat.format("yyyy-MM-dd", dateAttendance);

                        date = (String) strDate;

                        if (NetworkUtils.isNetworkAvailable(PrescriptionHistoryActivity.this))
                            prescriptionListCall();
                        else
                            NetworkUtils.isNetworkNotAvailable(PrescriptionHistoryActivity.this);

                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });


    }

    private void prescriptionListCall() {

        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.prescriptionHistory(doctor_id,patient_id, user_id);
        if (patient_id==null)
        {
            patient_id=getIntent().getStringExtra("id");
        }
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
                            patientPrescriptionHistoryModelArrayList.clear();
                            if (errorCode.equalsIgnoreCase("1"))
                            {
                                patientPrescriptionHistoryModelArrayList = successModel.getPatientPrescriptionHistoryModelArrayList();

                                if (patientPrescriptionHistoryModelArrayList.size()!= 0)
                                {
                                    prescriptionHistoryAdapter = new PrescriptionHistoryAdapter(PrescriptionHistoryActivity.this,patientPrescriptionHistoryModelArrayList);
                                    recyclerView_prescription.setAdapter(prescriptionHistoryAdapter);
                                    prescriptionHistoryAdapter.notifyDataSetChanged();

                                    recyclerView_prescription.setVisibility(View.VISIBLE);
                                    iv_empty_list.setVisibility(View.GONE);
                                } else {
                                    recyclerView_prescription.setVisibility(View.GONE);
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
