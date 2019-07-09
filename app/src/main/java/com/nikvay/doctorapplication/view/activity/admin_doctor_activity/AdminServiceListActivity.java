package com.nikvay.doctorapplication.view.activity.admin_doctor_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.ServiceListModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.ServiceAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminServiceListActivity extends AppCompatActivity {

    private FloatingActionButton fabAddService;
    private RecyclerView recyclerServiceList;
    private ArrayList<ServiceListModel> serviceListModelArrayList = new ArrayList<>();
    private ServiceAdapter serviceAdapter;
    private ImageView iv_no_data_found,iv_close;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private String user_id,TAG = getClass().getSimpleName();
    private SwipeRefreshLayout refreshServiceAdmin;
    private EditText edt_search_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service_list);


        find_All_IDs();
        events();
    }

    private void find_All_IDs() {
        recyclerServiceList=findViewById(R.id.recyclerServiceList);
        fabAddService=findViewById(R.id.fabAddService);
        iv_no_data_found=findViewById(R.id.iv_no_data_found);
        iv_close=findViewById(R.id.iv_close);
        edt_search_service=findViewById(R.id.edt_search_service);
        refreshServiceAdmin=findViewById(R.id.refreshServiceAdmin);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        errorMessageDialog= new ErrorMessageDialog(AdminServiceListActivity.this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AdminServiceListActivity.this);
        recyclerServiceList.setLayoutManager(linearLayoutManager);

        doctorModelArrayList= SharedUtils.getUserDetails(AdminServiceListActivity.this);
        user_id =doctorModelArrayList.get(0).getUser_id();


        if (NetworkUtils.isNetworkAvailable(AdminServiceListActivity.this))
            callListService(user_id);
        else
            NetworkUtils.isNetworkNotAvailable(AdminServiceListActivity.this);

    }

    private void callListService(String user_id)
    {

        Call<SuccessModel> call = apiInterface.listService(user_id);

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
                            serviceListModelArrayList.clear();

                            if (code.equalsIgnoreCase("1")) {

                                serviceListModelArrayList=successModel.getServiceListModelArrayList();

                                if(serviceListModelArrayList.size()!=0) {

                                    serviceAdapter=new ServiceAdapter(AdminServiceListActivity.this,serviceListModelArrayList);
                                    recyclerServiceList.setAdapter(serviceAdapter);
                                   // recyclerServiceList.addItemDecoration(new DividerItemDecoration(AdminServiceListActivity.this, DividerItemDecoration.VERTICAL));
                                    recyclerServiceList.setHasFixedSize(true);
                                }
                                else
                                {
                                    errorMessageDialog.showDialog("List is Empty");
                                }

                            } else {
                                errorMessageDialog.showDialog("Response is null");
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


    private void events() {
        fabAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminServiceListActivity.this, AddServiceActivity.class);
                startActivity(intent);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        refreshServiceAdmin.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (NetworkUtils.isNetworkAvailable(AdminServiceListActivity.this))
                    callListService(user_id);
                else
                    NetworkUtils.isNetworkNotAvailable(AdminServiceListActivity.this);


                refreshServiceAdmin.setRefreshing(false);
            }
        });


        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String search=edt_search_service.getText().toString().trim();
                if(!search.equalsIgnoreCase(""))
                {
                    serviceAdapter.getFilter().filter(search);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
