package com.nikvay.doctorapplication.view.fragment.doctor_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.NotificationListModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.ShowProgress;
import com.nikvay.doctorapplication.utils.SuccessMessageDialog;
import com.nikvay.doctorapplication.view.adapter.doctor_adapter.NotificationListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    Context mContext;
    private RecyclerView recyclerViewNotification;
    private ArrayList<NotificationListModel> notificationListModelArrayList=new ArrayList<>();
    private NotificationListAdapter notificationListAdapter;
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();
    private String doctor_id,user_id,TAG = getClass().getSimpleName(),doctor_name;
    private ApiInterface apiInterface;
    private ImageView iv_notification;
    private ErrorMessageDialog errorMessageDialog;
    private Button btnNotificationClear;
    ShowProgress showProgress;
    private SuccessMessageDialog successMessageDialog;
    private SwipeRefreshLayout refreshNotification;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        mContext = getActivity();

        find_All_IDs(view);
        events();
        return view;
    }
    private void find_All_IDs(View view) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerViewNotification=view.findViewById(R.id.recyclerViewNotification);
        iv_notification=view.findViewById(R.id.iv_notification);
        btnNotificationClear=view.findViewById(R.id.btnNotificationClear);
        refreshNotification=view.findViewById(R.id.refreshNotification);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerViewNotification.setLayoutManager(linearLayoutManager);
        recyclerViewNotification.setHasFixedSize(true);

        doctorModelArrayList= SharedUtils.getUserDetails(mContext);
        doctor_id=doctorModelArrayList.get(0).getDoctor_id();
        user_id=doctorModelArrayList.get(0).getUser_id();
        doctor_name=doctorModelArrayList.get(0).getName();
        errorMessageDialog= new ErrorMessageDialog(mContext);
        successMessageDialog = new SuccessMessageDialog(mContext);
        showProgress=new ShowProgress(mContext);

        if (NetworkUtils.isNetworkAvailable(mContext))
           notificationList();
        else
            NetworkUtils.isNetworkNotAvailable(mContext);



    }

    private void notificationList() {
        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.notificationList(doctor_id,user_id);
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


                            if (code.equalsIgnoreCase("1")) {

                                notificationListModelArrayList=successModel.getNotificationListModelArrayList();

                                if(notificationListModelArrayList.size()!=0) {

                                    notificationListAdapter=new NotificationListAdapter(mContext,notificationListModelArrayList,doctor_name);
                                    recyclerViewNotification.setAdapter(notificationListAdapter);
                                    iv_notification.setVisibility(View.GONE);
                                    notificationListAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    btnNotificationClear.setVisibility(View.GONE);
                                    iv_notification.setVisibility(View.VISIBLE);
                                    notificationListAdapter.notifyDataSetChanged();
                                }

                            } else {
                                errorMessageDialog.showDialog("Response Not Found");
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

    private void events() {
        btnNotificationClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationClear();
            }
        });

        refreshNotification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (NetworkUtils.isNetworkAvailable(mContext))
                    notificationList();
                else
                    NetworkUtils.isNetworkNotAvailable(mContext);
                refreshNotification.setRefreshing(false);
            }
        });
    }

    private void notificationClear() {
        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.notificationClear(doctor_id,user_id);
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


                            if (code.equalsIgnoreCase("1")) {
                                successMessageDialog.showDialog("Notification Clear Successfully");
                            } else {
                                errorMessageDialog.showDialog("Response Not Found");
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
