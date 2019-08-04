package com.nikvay.saipooja_doctor.view.activity.admin_doctor_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.ClassModel;
import com.nikvay.saipooja_doctor.model.DoctorListModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.utils.StaticContent;
import com.nikvay.saipooja_doctor.view.adapter.admin_doctor_adapter.DoctorListAdapter;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.DoctorsAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInstructor extends AppCompatActivity {
ImageView iv_close;
    RecyclerView recyclerView;
    private ApiInterface apiInterface;
    ShowProgress showProgress;
    ClassModel classModel;
    DoctorsAdapter doctorsAdapter;
    private String TAG = getClass().getSimpleName();
    private ErrorMessageDialog errorMessageDialog;

    private ArrayList<DoctorListModel> doctorListModelArrayList = new ArrayList<>();
    private DoctorListAdapter doctorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        find_all_ids();
events();
    }

    private void events()
    {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void find_all_ids()
    {
        iv_close=findViewById(R.id.iv_close);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            classModel = (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        recyclerView=findViewById(R.id.instructors_list);
        if (NetworkUtils.isNetworkAvailable(AddInstructor.this))
            callDoctorList();
        else
            NetworkUtils.isNetworkNotAvailable(AddInstructor.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddInstructor.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void callDoctorList()
    {
        //   showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.doctorList();
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

                            doctorListModelArrayList.clear();
                            if (code.equalsIgnoreCase("1")) {

                                doctorListModelArrayList = successModel.getDoctorListModelArrayList();

                                if (doctorListModelArrayList.size() != 0) {
                                    Collections.reverse(doctorListModelArrayList);

                                    doctorsAdapter = new DoctorsAdapter(AddInstructor.this, doctorListModelArrayList);
                                    recyclerView.setAdapter(doctorsAdapter);
                                   /* edt_search_doctor.setEnabled(true);
                                    iv_no_data_found.setVisibility(View.GONE);
                                  */  doctorsAdapter.notifyDataSetChanged();
                                    //recyclerViewDoctorList.addItemDecoration(new DividerItemDecoration(DoctorListActivity.this, DividerItemDecoration.VERTICAL));

                                } else {/*
                                    edt_search_doctor.setEnabled(false);
                                    iv_no_data_found.setVisibility(View.VISIBLE);*/
                                    doctorsAdapter.notifyDataSetChanged();
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
