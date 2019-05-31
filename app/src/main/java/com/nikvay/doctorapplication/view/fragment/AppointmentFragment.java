package com.nikvay.doctorapplication.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.apicallcommon.ApiClient;
import com.nikvay.doctorapplication.apicallcommon.ApiInterface;
import com.nikvay.doctorapplication.model.DoctorModel;
import com.nikvay.doctorapplication.model.SuccessModel;
import com.nikvay.doctorapplication.utils.ErrorMessageDialog;
import com.nikvay.doctorapplication.utils.NetworkUtils;
import com.nikvay.doctorapplication.utils.SharedUtils;
import com.nikvay.doctorapplication.utils.StaticContent;
import com.nikvay.doctorapplication.view.activity.AppointmentListActivity;
import com.nikvay.doctorapplication.view.activity.ServiceListActivity;
import com.nikvay.doctorapplication.view.adapter.ServiceListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppointmentFragment extends Fragment {

    Context mContext;
    private CardView cardViewPending, cardViewConfirmed, cardViewCancelled, cardViewArchive;
    private FloatingActionButton fabAddNewAppointment;
    private TextView textPending, textConfirmed, textCancelled, textArchive,textPendingCount,textConfirmCount,textCancelCount,textArchiveCount;
    private ApiInterface apiInterface;
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private String doctor_id,TAG = getClass().getSimpleName();
    private ErrorMessageDialog errorMessageDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        mContext = getActivity();

        find_All_IDs(view);
        events();
        if (NetworkUtils.isNetworkAvailable(mContext))
            callAppointmentCount();
        else
            NetworkUtils.isNetworkNotAvailable(mContext);


        return view;
    }



    private void find_All_IDs(View view) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        cardViewPending = view.findViewById(R.id.cardViewPending);
        cardViewConfirmed = view.findViewById(R.id.cardViewConfirmed);
        cardViewCancelled = view.findViewById(R.id.cardViewCancelled);
        cardViewArchive = view.findViewById(R.id.cardViewArchive);
        fabAddNewAppointment = view.findViewById(R.id.fabAddNewAppointment);

        textPending = view.findViewById(R.id.textPending);
        textConfirmed = view.findViewById(R.id.textConfirmed);
        textCancelled = view.findViewById(R.id.textCancelled);
        textArchive = view.findViewById(R.id.textArchive);

        textPendingCount = view.findViewById(R.id.textPendingCount);
        textConfirmCount = view.findViewById(R.id.textConfirmCount);
        textCancelCount = view.findViewById(R.id.textCancelCount);
        textArchiveCount = view.findViewById(R.id.textArchiveCount);

        errorMessageDialog= new ErrorMessageDialog(mContext);

        doctorModelArrayList= SharedUtils.getUserDetails(mContext);
        doctor_id=doctorModelArrayList.get(0).getDoctor_id();

    }

    private void events() {
        cardViewPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppointmentListActivity.class);
                intent.putExtra(StaticContent.IntentKey.STATUS, "0");
                intent.putExtra(StaticContent.IntentKey.APPOINTMENT,textPending.getText().toString().trim());
                startActivity(intent);
            }
        });

        cardViewArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AppointmentListActivity.class);
                intent.putExtra(StaticContent.IntentKey.STATUS, "3");
                intent.putExtra(StaticContent.IntentKey.APPOINTMENT,textArchive.getText().toString().trim());
                startActivity(intent);

            }
        });
        cardViewCancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppointmentListActivity.class);
                intent.putExtra(StaticContent.IntentKey.STATUS, "2");
                intent.putExtra(StaticContent.IntentKey.APPOINTMENT,textCancelled.getText().toString().trim());
                startActivity(intent);
            }
        });

        cardViewConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AppointmentListActivity.class);
                intent.putExtra(StaticContent.IntentKey.STATUS, "1");
                intent.putExtra(StaticContent.IntentKey.APPOINTMENT,textConfirmed.getText().toString().trim());
                startActivity(intent);

            }
        });

        fabAddNewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServiceListActivity.class);
                intent.putExtra(StaticContent.IntentKey.APPOINTMENT,StaticContent.IntentValue.APPOINTMENT);
                startActivity(intent);
            }
        });
    }
    private void callAppointmentCount() {
        Call<SuccessModel> call = apiInterface.appointmentListCount(doctor_id);
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

                        String message = null, code = null,strPending,strConfirmed,strCancelled,strCompleted;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();
                            strPending=successModel.getAppointment_for_Pending();
                            strCancelled=successModel.getAppointment_for_Cancelled();
                            strConfirmed=successModel.getAppointment_for_Confirmed();
                            strCompleted=successModel.getAppointment_for_Completed();
                            textPendingCount.setText(strPending);
                            textArchiveCount.setText(strCompleted);
                            textCancelCount.setText(strCancelled);
                            textConfirmCount.setText(strConfirmed);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                //showProgress.dismissDialog();
                errorMessageDialog.showDialog(t.getMessage());
            }
        });

    }

}
