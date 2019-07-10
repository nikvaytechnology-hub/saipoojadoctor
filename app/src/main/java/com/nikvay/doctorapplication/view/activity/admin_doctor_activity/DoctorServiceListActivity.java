package com.nikvay.doctorapplication.view.activity.admin_doctor_activity;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.doctor_activity.NewAddServiceActivity;
import com.nikvay.doctorapplication.view.adapter.admin_doctor_adapter.DoctorServiceListAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorServiceListActivity extends AppCompatActivity {


    private RecyclerView recyclerViewServiceList;
    ArrayList<ServiceModel> serviceModelArrayList=new ArrayList<>();
    private DoctorServiceListAdapter doctorServiceListAdapter;
    private ImageView iv_close,iv_no_data_found;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private String TAG = getClass().getSimpleName(),doctor_id,super_doctor_id;
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private FloatingActionButton fabAddService;
    private TextView textTitleServiceName;
    private EditText edt_search_service;
    ShowProgress showProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_service_list);

        find_All_IDs();
        events();

        if (NetworkUtils.isNetworkAvailable(DoctorServiceListActivity.this))
            callServiceList();
        else
            NetworkUtils.isNetworkNotAvailable(DoctorServiceListActivity.this);
    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fabAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorServiceListActivity.this, NewAddServiceActivity.class);
                startActivity(intent);
            }
        });

        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                doctorServiceListAdapter.getFilter().filter(edt_search_service.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerViewServiceList=findViewById(R.id.recyclerViewServiceList);
        iv_close=findViewById(R.id.iv_close);
        fabAddService=findViewById(R.id.fabAddService);
        textTitleServiceName=findViewById(R.id.textTitleServiceName);
        iv_no_data_found=findViewById(R.id.iv_no_data_found);
        edt_search_service=findViewById(R.id.edt_search_service);

        doctorModelArrayList= SharedUtils.getUserDetails(DoctorServiceListActivity.this);
        doctor_id=doctorModelArrayList.get(0).getDoctor_id();

        errorMessageDialog= new ErrorMessageDialog(DoctorServiceListActivity.this);
        showProgress=new ShowProgress(DoctorServiceListActivity.this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            doctor_id = bundle.getString(StaticContent.IntentKey.SERVICE_DETAIL);

        }


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DoctorServiceListActivity.this);
        recyclerViewServiceList.setLayoutManager(linearLayoutManager);
        recyclerViewServiceList.setHasFixedSize(true);



    }
    private void callServiceList() {
        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.serviceList(doctor_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        serviceModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                serviceModelArrayList=successModel.getServiceModelArrayList();

                                if(serviceModelArrayList.size()!=0) {
                                    Collections.reverse(serviceModelArrayList);
                                    doctorServiceListAdapter = new DoctorServiceListAdapter(DoctorServiceListActivity.this, serviceModelArrayList);
                                    recyclerViewServiceList.setAdapter(doctorServiceListAdapter);
                                    iv_no_data_found.setVisibility(View.GONE);

                                    doctorServiceListAdapter.notifyDataSetChanged();
                                    // recyclerViewServiceList.addItemDecoration(new DividerItemDecoration(ServiceListActivity.this, DividerItemDecoration.VERTICAL));
                                }
                                else
                                {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                    doctorServiceListAdapter.notifyDataSetChanged();
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
        super.onResume();
        //callServiceList();
    }
}
