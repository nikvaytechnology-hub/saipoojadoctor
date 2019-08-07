package com.nikvay.saipoojadoctor.view.activity.doctor_activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipoojadoctor.R;
import com.nikvay.saipoojadoctor.apicallcommon.ApiClient;
import com.nikvay.saipoojadoctor.apicallcommon.ApiInterface;
import com.nikvay.saipoojadoctor.model.DoctorModel;
import com.nikvay.saipoojadoctor.model.PatientModel;
import com.nikvay.saipoojadoctor.model.ServiceModel;
import com.nikvay.saipoojadoctor.model.SuccessModel;
import com.nikvay.saipoojadoctor.utils.ErrorMessageDialog;
import com.nikvay.saipoojadoctor.utils.NetworkUtils;
import com.nikvay.saipoojadoctor.utils.SharedUtils;
import com.nikvay.saipoojadoctor.utils.ShowProgress;
import com.nikvay.saipoojadoctor.utils.StaticContent;
import com.nikvay.saipoojadoctor.view.adapter.doctor_adapter.PatientAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientActivity extends AppCompatActivity {

    private RecyclerView recyclerPatientList;
    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    private PatientAdapter patientAdapter;
    private FloatingActionButton fabAddPatient;
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private String doctor_id, TAG = getClass().getSimpleName(),appointmentName="Service List",date="",time="";
    private ImageView iv_close,iv_no_data_found;
    private TextView textTitlePatientName;
    private ServiceModel serviceModel;
    ShowProgress showProgress;
    private SwipeRefreshLayout refreshPatient;
    private EditText edt_search_patient;
    public static boolean isAdded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        find_All_IDs();
        events();
    }

    private void events() {
        fabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext, "Add Patient", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PatientActivity.this, NewPatientActivity.class);
                startActivity(intent);

            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        edt_search_patient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String search=edt_search_patient.getText().toString().trim();
                if(!search.equalsIgnoreCase(""))
                {
                    patientAdapter.getFilter().filter(search);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        refreshPatient.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (NetworkUtils.isNetworkAvailable(PatientActivity.this))
                    callListPatient();
                else
                    NetworkUtils.isNetworkNotAvailable(PatientActivity.this);
                refreshPatient.setRefreshing(false);
            }
        });


    }

    private void find_All_IDs() {
        recyclerPatientList = findViewById(R.id.recyclerPatientList);
        fabAddPatient = findViewById(R.id.fabAddPatient);
        iv_close = findViewById(R.id.iv_close);
        textTitlePatientName = findViewById(R.id.textTitlePatientName);
        iv_no_data_found = findViewById(R.id.iv_no_data_found);
        edt_search_patient = findViewById(R.id.edt_search_patient);
        refreshPatient = findViewById(R.id.refreshPatient);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        errorMessageDialog = new ErrorMessageDialog(PatientActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PatientActivity.this);
        recyclerPatientList.setLayoutManager(linearLayoutManager);
        recyclerPatientList.setHasFixedSize(true);
        showProgress=new ShowProgress(PatientActivity.this);

        doctorModelArrayList = SharedUtils.getUserDetails(PatientActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            appointmentName = bundle.getString(StaticContent.IntentKey.APPOINTMENT);
            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            date=bundle.getString(StaticContent.IntentKey.DATE);
            time=bundle.getString(StaticContent.IntentKey.TIME);
            //Toast.makeText(this, serviceModel.getS_name()+" "+date+" "+time, Toast.LENGTH_SHORT).show();
            textTitlePatientName.setText("Select Patient");
        }


        if (NetworkUtils.isNetworkAvailable(PatientActivity.this))
            callListPatient();
        else
            NetworkUtils.isNetworkNotAvailable(PatientActivity.this);


    }

    private void callListPatient() {
        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.patientList(doctor_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        patientModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                patientModelArrayList = successModel.getPatientModelArrayList();

                                if (patientModelArrayList.size() != 0) {

                                    Collections.reverse(patientModelArrayList);
                                    patientAdapter = new PatientAdapter(PatientActivity.this, patientModelArrayList,appointmentName,serviceModel,date,time);
                                    recyclerPatientList.setAdapter(patientAdapter);
                                    patientAdapter.notifyDataSetChanged();
                                   // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                    patientAdapter.notifyDataSetChanged();
                                }

                            } else {
                                errorMessageDialog.showDialog("Response Not Working");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();
                errorMessageDialog.showDialog(t.getMessage());
            }
        });

    }

    /*@Override
    protected void onPostResume() {
        super.onPostResume();
        if (isAdded) {
            isAdded = false;

            if (NetworkUtils.isNetworkAvailable(PatientActivity.this))
                callListPatient();
            else
                NetworkUtils.isNetworkNotAvailable(PatientActivity.this);

        }
    }*/
}
