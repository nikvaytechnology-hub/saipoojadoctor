package com.nikvay.saipooja_doctor.view.fragment.admin_doctor_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.saipooja_doctor.apicallcommon.ApiClient;
import com.nikvay.saipooja_doctor.apicallcommon.ApiInterface;
import com.nikvay.saipooja_doctor.model.DepartmentModel;
import com.nikvay.saipooja_doctor.model.DoctorModel;
import com.nikvay.saipooja_doctor.utils.ErrorMessageDialog;
import com.nikvay.saipooja_doctor.utils.NetworkUtils;
import com.nikvay.saipooja_doctor.utils.SharedUtils;
import com.nikvay.saipooja_doctor.model.SuccessModel;
import com.nikvay.saipooja_doctor.view.activity.admin_doctor_activity.AddDepartmentActivity;
import com.nikvay.saipooja_doctor.view.adapter.admin_doctor_adapter.DepartmentListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DepartmentFragment extends Fragment {


    View view;
    private FloatingActionButton fabAddDepartment;
    RecyclerView recyclerDepartmentList;
    ArrayList<DepartmentModel> departmentModelArrayList = new ArrayList<>();
    DepartmentListAdapter departmentListAdapter;

    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private String user_id,TAG = getClass().getSimpleName();
    private EditText edt_search_patient;
    Context mContext;
    private SwipeRefreshLayout refreshDepartment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_department, container, false);
        mContext =getActivity();
        find_All_IDs(view);
        events(view);

        return  view;
    }

    private void events(View view) {
        fabAddDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_department=new Intent(mContext, AddDepartmentActivity.class);
                startActivity(intent_department);
            }
        });


        edt_search_patient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                departmentListAdapter.getFilter().filter(edt_search_patient.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        refreshDepartment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isNetworkAvailable(mContext))
                    callListDepartment();
                else
                    NetworkUtils.isNetworkNotAvailable(mContext);
                refreshDepartment.setRefreshing(false);
            }
        });
    }

    private void find_All_IDs(View view) {
        recyclerDepartmentList=view.findViewById(R.id.recyclerDepartmentList);
        fabAddDepartment=view.findViewById(R.id.fabAddDepartment);
        edt_search_patient=view.findViewById(R.id.edt_search_patient);
        refreshDepartment=view.findViewById(R.id.refreshDepartment);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        errorMessageDialog= new ErrorMessageDialog(mContext);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerDepartmentList.setLayoutManager(linearLayoutManager);

        doctorModelArrayList= SharedUtils.getUserDetails(mContext);
        user_id =doctorModelArrayList.get(0).getUser_id();


        if (NetworkUtils.isNetworkAvailable(mContext))
            callListDepartment();
        else
            NetworkUtils.isNetworkNotAvailable(mContext);

    }

    private void callListDepartment() {

        Call<SuccessModel> call = apiInterface.listDepartment(user_id);

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
                            departmentModelArrayList.clear();

                            if (code.equalsIgnoreCase("1")) {

                                departmentModelArrayList=successModel.getDepartmentModelArrayList();

                                if(departmentModelArrayList.size()!=0) {

                                    departmentListAdapter=new DepartmentListAdapter(mContext,departmentModelArrayList,false);
                                    recyclerDepartmentList.setAdapter(departmentListAdapter);
                                    //recyclerDepartmentList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                                    edt_search_patient.setEnabled(true);
                                    recyclerDepartmentList.setHasFixedSize(true);
                                    departmentListAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    errorMessageDialog.showDialog("List is Empty");
                                    edt_search_patient.setEnabled(false);
                                    departmentListAdapter.notifyDataSetChanged();
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

}
