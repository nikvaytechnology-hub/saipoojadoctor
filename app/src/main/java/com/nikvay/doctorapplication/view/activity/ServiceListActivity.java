package com.nikvay.doctorapplication.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.ServiceModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.adapter.PatientAdapter;
import com.nikvay.doctorapplication.view.adapter.ServiceListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceListActivity extends AppCompatActivity {


    private RecyclerView recyclerViewServiceList;
    ArrayList<ServiceModel> serviceModelArrayList=new ArrayList<>();
    private ServiceListAdapter serviceListAdapter;
    private ImageView  iv_close;
    private ApiInterface apiInterface;
    ProgressDialog pd;
    private ShowProgress showProgress;
    private ErrorMessageDialog errorMessageDialog;
    private String device_token,TAG = getClass().getSimpleName(),doctor_id,appointmentName="Service List";
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private FloatingActionButton fabAddService;
    private TextView textTitleServiceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);


        find_All_IDs();
        events();

        if (NetworkUtils.isNetworkAvailable(ServiceListActivity.this))
            callServiceList();
        else
            NetworkUtils.isNetworkNotAvailable(ServiceListActivity.this);

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
                Intent intent = new Intent(ServiceListActivity.this,NewAddServiceActivity.class);
                startActivity(intent);
            }
        });

    }

    private void find_All_IDs() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerViewServiceList=findViewById(R.id.recyclerViewServiceList);
        iv_close=findViewById(R.id.iv_close);
        fabAddService=findViewById(R.id.fabAddService);
        textTitleServiceName=findViewById(R.id.textTitleServiceName);

        doctorModelArrayList= SharedUtils.getUserDetails(ServiceListActivity.this);
        doctor_id=doctorModelArrayList.get(0).getDoctor_id();

        errorMessageDialog= new ErrorMessageDialog(ServiceListActivity.this);

        showProgress=new ShowProgress(ServiceListActivity.this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            appointmentName = bundle.getString(StaticContent.IntentKey.APPOINTMENT);
            textTitleServiceName.setText("Select Service");
        }


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ServiceListActivity.this);
        recyclerViewServiceList.setLayoutManager(linearLayoutManager);



    }
    private void callServiceList() {

        /*showProgress.showDialog();*/
        /*pd = new ProgressDialog(this);
        pd.setMessage("Loading Please Wait...");
        pd.setCancelable(false);
        pd.show();*/
        Call<SuccessModel> call = apiInterface.serviceList(doctor_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
               // showProgress.dismissDialog();
               // pd.dismiss();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();

                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                serviceModelArrayList=successModel.getServiceModelArrayList();

                                if(doctorModelArrayList.size()!=0) {

                                    serviceListAdapter = new ServiceListAdapter(ServiceListActivity.this, serviceModelArrayList,appointmentName);
                                    recyclerViewServiceList.setAdapter(serviceListAdapter);
                                    recyclerViewServiceList.addItemDecoration(new DividerItemDecoration(ServiceListActivity.this, DividerItemDecoration.VERTICAL));
                                    recyclerViewServiceList.setHasFixedSize(true);
                                }
                                else
                                {
                                    errorMessageDialog.showDialog("List Not found");
                                }

                            } else {
                                errorMessageDialog.showDialog("List Not found");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                //showProgress.dismissDialog();
               pd.dismiss();
                errorMessageDialog.showDialog(t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        callServiceList();
    }
}
