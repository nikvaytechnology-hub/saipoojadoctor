package com.nikvay.saipooja_doctor.view.activity.doctor_activity;

import android.os.Bundle;
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
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.model.EnquiryListModel;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.utils.ShowProgress;
import com.nikvay.saipooja_doctor.view.adapter.doctor_adapter.EnquiryListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryActivity extends AppCompatActivity {

    private ImageView iv_close,iv_no_data_found;
    private RecyclerView  recyclerViewEnquiryList;
    private ApiInterface apiInterface;
    ShowProgress showProgress;
    private ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String doctor_id,TAG = getClass().getSimpleName();
    private ArrayList<EnquiryListModel> enquiryListModelArrayList=new ArrayList<>();
    private ErrorMessageDialog errorMessageDialog;
    private EnquiryListAdapter enquiryListAdapter;
    private EditText edt_search_enquiry;
    private SwipeRefreshLayout refreshEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);


        find_All_Ids();
        events();
    }

    private void find_All_Ids() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        errorMessageDialog=new ErrorMessageDialog(EnquiryActivity.this);
        iv_close=findViewById(R.id.iv_close);
        showProgress = new ShowProgress(EnquiryActivity.this);
        recyclerViewEnquiryList=findViewById(R.id.recyclerViewEnquiryList);
        iv_no_data_found=findViewById(R.id.iv_no_data_found);
        edt_search_enquiry=findViewById(R.id.edt_search_enquiry);
        refreshEnquiry=findViewById(R.id.refreshEnquiry);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(EnquiryActivity.this);
        recyclerViewEnquiryList.setLayoutManager(linearLayoutManager);
        recyclerViewEnquiryList.setHasFixedSize(true);


        doctorModelArrayList = SharedUtils.getUserDetails(EnquiryActivity.this);
        doctor_id = doctorModelArrayList.get(0).getDoctor_id();


        if (NetworkUtils.isNetworkAvailable(EnquiryActivity.this))
            enquiryList();
        else
            NetworkUtils.isNetworkNotAvailable(EnquiryActivity.this);

    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        edt_search_enquiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                enquiryListAdapter.getFilter().filter(edt_search_enquiry.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        refreshEnquiry.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isNetworkAvailable(EnquiryActivity.this))
                    enquiryList();
                else
                    NetworkUtils.isNetworkNotAvailable(EnquiryActivity.this);
                refreshEnquiry.setRefreshing(false);
            }
        });
    }

    private void enquiryList() {


        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.enquiryList(doctor_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        enquiryListModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();

                            if (code.equalsIgnoreCase("1")) {

                                enquiryListModelArrayList=successModel.getEnquiryListModelArrayList();

                                if(enquiryListModelArrayList.size()!=0) {

                                    enquiryListAdapter = new EnquiryListAdapter(EnquiryActivity.this, enquiryListModelArrayList);
                                    recyclerViewEnquiryList.setAdapter(enquiryListAdapter);
                                    iv_no_data_found.setVisibility(View.GONE);
                                    enquiryListAdapter.notifyDataSetChanged();
                                    // recyclerClassList.addItemDecoration(new DividerItemDecoration(ClassActivity.this, DividerItemDecoration.VERTICAL));;
                                }
                                else
                                {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                    enquiryListAdapter.notifyDataSetChanged();
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
