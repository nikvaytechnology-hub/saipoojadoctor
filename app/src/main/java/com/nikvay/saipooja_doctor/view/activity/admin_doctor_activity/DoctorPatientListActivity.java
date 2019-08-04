package com.nikvay.saipooja_doctor.view.activity.admin_doctor_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.PatientModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.view.activity.doctor_activity.NewPatientActivity;
import com.nikvay.saipooja_doctor.view.adapter.admin_doctor_adapter.DoctorPatientAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPatientListActivity extends AppCompatActivity {


    private RecyclerView recyclerPatientList;
    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    private DoctorPatientAdapter doctorPatientAdapter;
    private FloatingActionButton fabAddPatient;
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private String doctor_id, TAG = getClass().getSimpleName(),super_doctor_id;
    private ImageView iv_close,iv_no_data_found;
    ShowProgress showProgress;
    private EditText edt_search_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_list);

        find_All_IDs();
        events();
    }


    private void events() {
        fabAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext, "Add Patient", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DoctorPatientListActivity.this, NewPatientActivity.class);
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
                    doctorPatientAdapter.getFilter().filter(search);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void find_All_IDs() {
        recyclerPatientList = findViewById(R.id.recyclerPatientList);
        fabAddPatient = findViewById(R.id.fabAddPatient);
        iv_close = findViewById(R.id.iv_close);
        iv_no_data_found = findViewById(R.id.iv_no_data_found);
        edt_search_patient = findViewById(R.id.edt_search_patient);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        errorMessageDialog = new ErrorMessageDialog(DoctorPatientListActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorPatientListActivity.this);
        recyclerPatientList.setLayoutManager(linearLayoutManager);
        recyclerPatientList.setHasFixedSize(true);
        showProgress=new ShowProgress(DoctorPatientListActivity.this);

        doctorModelArrayList = SharedUtils.getUserDetails(DoctorPatientListActivity.this);
        super_doctor_id = doctorModelArrayList.get(0).getDoctor_id();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            doctor_id = bundle.getString(StaticContent.IntentKey.PATIENT_DETAIL);

        }


        if (NetworkUtils.isNetworkAvailable(DoctorPatientListActivity.this))
            callListPatient();
        else
            NetworkUtils.isNetworkNotAvailable(DoctorPatientListActivity.this);


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
                                    doctorPatientAdapter = new DoctorPatientAdapter(DoctorPatientListActivity.this, patientModelArrayList) ;
                                    recyclerPatientList.setAdapter(doctorPatientAdapter);
                                    doctorPatientAdapter.notifyDataSetChanged();
                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                    doctorPatientAdapter.notifyDataSetChanged();
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

    @Override
    protected void onResume() {
        //callListPatient();
        super.onResume();
    }
}
