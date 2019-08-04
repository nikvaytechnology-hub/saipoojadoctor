package com.nikvay.saipooja_doctor.view.activity.admin_doctor_activity;

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

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.DoctorListModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.view.adapter.admin_doctor_adapter.DoctorListAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDoctorList;
    private ArrayList<DoctorListModel> doctorListModelArrayList = new ArrayList<>();
    private DoctorListAdapter doctorListAdapter;
    private ImageView iv_back, iv_no_data_found;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private String TAG = getClass().getSimpleName();
    private EditText edt_search_doctor;
    ShowProgress showProgress;
    private FloatingActionButton fabAddDoctor;
    private SwipeRefreshLayout refreshDoctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);


        find_All_ID();
        event();
    }

    private void event() {

        edt_search_doctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                doctorListAdapter.getFilter().filter(edt_search_doctor.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fabAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoctorListActivity.this, AddDoctorActivity.class);
                startActivity(intent);
            }
        });

        refreshDoctorList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                edt_search_doctor.setText("");
                if (NetworkUtils.isNetworkAvailable(DoctorListActivity.this))
                    callDoctorList();
                else
                    NetworkUtils.isNetworkNotAvailable(DoctorListActivity.this);
                refreshDoctorList.setRefreshing(false);
            }
        });

    }

    private void find_All_ID() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerViewDoctorList = findViewById(R.id.recyclerViewDoctorList);
        iv_back = findViewById(R.id.iv_back);
        iv_no_data_found = findViewById(R.id.iv_no_data_found);
        edt_search_doctor = findViewById(R.id.edt_search_doctor);
        fabAddDoctor = findViewById(R.id.fabAddDoctor);
        refreshDoctorList = findViewById(R.id.refreshDoctorList);

        errorMessageDialog = new ErrorMessageDialog(DoctorListActivity.this);
        showProgress = new ShowProgress(DoctorListActivity.this);

        if (NetworkUtils.isNetworkAvailable(DoctorListActivity.this))
            callDoctorList();
        else
            NetworkUtils.isNetworkNotAvailable(DoctorListActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorListActivity.this);
        recyclerViewDoctorList.setLayoutManager(linearLayoutManager);
        recyclerViewDoctorList.setHasFixedSize(true);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        callDoctorList();
    }

    private void callDoctorList() {
        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.doctorList();
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();

                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();

                            doctorListModelArrayList.clear();
                            if (code.equalsIgnoreCase("1")) {

                                doctorListModelArrayList = successModel.getDoctorListModelArrayList();

                                if (doctorListModelArrayList.size() != 0) {
                                    Collections.reverse(doctorListModelArrayList);
                                    doctorListAdapter = new DoctorListAdapter(DoctorListActivity.this, doctorListModelArrayList,false);
                                    recyclerViewDoctorList.setAdapter(doctorListAdapter);
                                    edt_search_doctor.setEnabled(true);
                                    iv_no_data_found.setVisibility(View.GONE);
                                    doctorListAdapter.notifyDataSetChanged();
                                    //recyclerViewDoctorList.addItemDecoration(new DividerItemDecoration(DoctorListActivity.this, DividerItemDecoration.VERTICAL));

                                } else {
                                    edt_search_doctor.setEnabled(false);
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                    doctorListAdapter.notifyDataSetChanged();
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


}
