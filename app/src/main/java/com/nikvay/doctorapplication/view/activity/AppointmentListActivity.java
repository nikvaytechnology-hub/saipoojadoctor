package com.nikvay.doctorapplication.view.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.AppoinmentListModel;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.adapter.AppointmentListAdapter;
import com.nikvay.doctorapplication.view.adapter.ServiceListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentListActivity extends AppCompatActivity {

    private String label,appointmentName,doctor_id,TAG = getClass().getSimpleName(),user_id;
    private ImageView iv_close,iv_no_data_found;
    private TextView textAppointmentTitleName;
    private RecyclerView recyclerViewAppointmentList;
    private ErrorMessageDialog errorMessageDialog;
    private ArrayList<AppoinmentListModel> appoinmentListModelArrayList = new ArrayList<>();
    private AppointmentListAdapter appointmentListAdapter;
    private ApiInterface apiInterface;
    private ShowProgress showProgress;
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        find_All_IDs();
        events();


        if (NetworkUtils.isNetworkAvailable(AppointmentListActivity.this))
            appoinmentListCall();
        else
            NetworkUtils.isNetworkNotAvailable(AppointmentListActivity.this);

    }



    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close = findViewById(R.id.iv_close);
        textAppointmentTitleName = findViewById(R.id.textAppointmentTitleName);
        recyclerViewAppointmentList = findViewById(R.id.recyclerViewAppointmentList);
        iv_no_data_found = findViewById(R.id.iv_no_data_found);
        showProgress=new ShowProgress(AppointmentListActivity.this);
        errorMessageDialog = new ErrorMessageDialog(AppointmentListActivity.this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            label = bundle.getString(StaticContent.IntentKey.STATUS);
            appointmentName = bundle.getString(StaticContent.IntentKey.APPOINTMENT);
            textAppointmentTitleName.setText(appointmentName + " " + "Appointment");

        }


        doctorModelArrayList = SharedUtils.getUserDetails(AppointmentListActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();
        user_id = doctorModelArrayList.get(0).getUser_id();

        recyclerViewAppointmentList.setHasFixedSize(true);
        recyclerViewAppointmentList.setLayoutManager(new LinearLayoutManager(this));

    }
    private void appoinmentListCall() {
        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.appointmentList(doctor_id,label,user_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                 showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        appoinmentListModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                appoinmentListModelArrayList=successModel.getAppoinmentListModelArrayList();

                                if(appoinmentListModelArrayList.size()!=0) {

                                    appointmentListAdapter=new AppointmentListAdapter(AppointmentListActivity.this,appoinmentListModelArrayList);
                                    recyclerViewAppointmentList.setAdapter(appointmentListAdapter);
                                    appointmentListAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
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
